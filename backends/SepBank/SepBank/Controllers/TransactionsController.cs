using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using SepBank.Data;
using SepBank.Models;
using SepBank.Models.DTO;
using SepBank.Services;
using SepBank.Utils;

namespace SepBank.Controllers
{
    public class TransactionsController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly Dictionary<Guid, (DateTime Timestamp, Guid ReceiverId, double Amount)> _transactionRequests;
        private readonly TransactionService _transactionService;

        public TransactionsController(ApplicationDbContext context,
            Dictionary<Guid, (DateTime Timestamp, Guid ReceiverId, double Amount)> transactionRequests,
            TransactionService transactionService)
        {
            _context = context;
            _transactionRequests = transactionRequests;
            _transactionService = transactionService;
        }

        [HttpPost("MakePCCTransaction")]
        public IActionResult MakePCCTransaction([FromBody] PCCTransactionDTO transaction)
        {
            var senderAccount = _context.Accounts.Where(x => x.AccountNumber == transaction.SenderAccountNumber).FirstOrDefault();
            if (senderAccount == null)
            {
                return BadRequest("Sender account doesn't exist");
            }

            if (senderAccount.Level < transaction.Amount)
            {
                return BadRequest("Sender doesn't have enough money.");
            }

            senderAccount.Level -= transaction.Amount;

            Transaction newTransaction = new Transaction();
            newTransaction.Amount = transaction.Amount;
            newTransaction.IsConfirmed = true;
            newTransaction.SenderId = senderAccount.Id;
            newTransaction.ReceiverAccountNumber = transaction.ReceiverAccountNumber;

            _context.Add(newTransaction);
            _context.SaveChanges();

            return Ok();
        }

        [HttpGet("CheckTransactionStatus")]
        public async Task<IActionResult> CheckTransactionStatus([FromQuery] Guid transactionId)
        {
            var transactionStatus = _context.TransactionSuccesses.Where(x => x.TransactionID == transactionId).FirstOrDefault();
            if (transactionStatus == null)
            {
                return BadRequest("Transaction doesn't exist.");
            }

            return Ok(transactionStatus.IsSuccess);
        }

        [HttpGet("RequestTransaction")]
        public IActionResult RequestTransaction([FromQuery] Guid receiverId, [FromQuery] double amount)
        {
            if (amount <= 0)
            {
                return BadRequest("Invalid amount.");
            }

            var transactionId = Guid.NewGuid();
            var timestamp = DateTime.UtcNow;

            // Store the transaction request information
            _transactionRequests[transactionId] = (timestamp, receiverId, amount);

            return Ok(transactionId);
        }

        [HttpGet]
        public IActionResult MakeTransaction([FromQuery] Guid transactionId)
        {
            if (!_transactionRequests.TryGetValue(transactionId, out var transactionInfo))
            {
                return BadRequest("Invalid transaction ID.");
            }

            _transactionRequests.Remove(transactionId);

            var timeElapsed = DateTime.UtcNow - transactionInfo.Timestamp;
            if (timeElapsed.TotalMinutes > Consts.TimeOutMinutes)
            {
                return BadRequest("Transaction ID has expired.");
            }

            var dto = new MakeTransactionDTO();
            dto.MakeTransactionId = transactionId;
            dto.Amount = transactionInfo.Amount;
            dto.ReceiverId = transactionInfo.ReceiverId;
            dto.RequestStart = transactionInfo.Timestamp;

            return View(dto);
        }


        // POST: Transactions/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> MakeTransaction([Bind("ReceiverId, SenderAccountNumber, SenderExpirationMonth, SenderExpirationYear, SenderSecurityNumber, Amount, RequestStart, MakeTransactionId")] MakeTransactionDTO dto)
        {
            if (ModelState.IsValid)
            {
                var timeElapsed = DateTime.UtcNow - dto.RequestStart;
                if (timeElapsed.TotalMinutes > Consts.TimeOutMinutes * 2)
                {
                    ModelState.AddModelError("", "Transaction request has expired.");
                    return View(dto);
                }

                if (await _transactionService.TryMakeTransactionAsync(dto))
                {
                    SaveTransactionResult(true, dto.MakeTransactionId);

                    return View("Result", "TransactionSuccess"); // Redirect to a success page
                }
                else
                {
                    SaveTransactionResult(false, dto.MakeTransactionId);

                    ModelState.AddModelError("", "Transaction failed. Please check the details and try again.");
                }
            }
            return View(dto);
        }

        private async Task SaveTransactionResult(bool isSuccess, Guid transactionId)
        {
            var existingTransaction = _context.TransactionSuccesses.Where(x => x.TransactionID == transactionId).FirstOrDefault();
            if (existingTransaction == null)
            {
                TransactionSuccess transactionSuccess = new();
                transactionSuccess.IsSuccess = isSuccess;
                transactionSuccess.TransactionID = transactionId;

                _context.Add(transactionSuccess);
                await _context.SaveChangesAsync();
            }
            else
            {
                //This shouldn't happen, but use the row if it is present
                existingTransaction.IsSuccess = isSuccess;
                _context.Update(existingTransaction);
                await _context.SaveChangesAsync();
            }
        }

        // GET: Transactions
        public async Task<IActionResult> Index()
        {
            return _context.Transactions != null ?
                        View(await _context.Transactions.ToListAsync()) :
                        Problem("Entity set 'ApplicationDbContext.Transaction'  is null.");
        }

        // GET: Transactions/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.Transactions == null)
            {
                return NotFound();
            }

            var transaction = await _context.Transactions
                .FirstOrDefaultAsync(m => m.Id == id);
            if (transaction == null)
            {
                return NotFound();
            }

            return View(transaction);
        }

        // GET: Transactions/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Transactions/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ReceiverId,SenderId,Amount,IsConfirmed,Id,RowVersion")] Transaction transaction)
        {
            if (ModelState.IsValid)
            {
                transaction.Id = Guid.NewGuid();
                _context.Add(transaction);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(transaction);
        }

        // GET: Transactions/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.Transactions == null)
            {
                return NotFound();
            }

            var transaction = await _context.Transactions.FindAsync(id);
            if (transaction == null)
            {
                return NotFound();
            }
            return View(transaction);
        }

        // POST: Transactions/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("ReceiverId,SenderId,Amount,IsConfirmed,Id,RowVersion")] Transaction transaction)
        {
            if (id != transaction.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(transaction);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!TransactionExists(transaction.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(transaction);
        }

        // GET: Transactions/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.Transactions == null)
            {
                return NotFound();
            }

            var transaction = await _context.Transactions
                .FirstOrDefaultAsync(m => m.Id == id);
            if (transaction == null)
            {
                return NotFound();
            }

            return View(transaction);
        }

        // POST: Transactions/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            if (_context.Transactions == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Transaction'  is null.");
            }
            var transaction = await _context.Transactions.FindAsync(id);
            if (transaction != null)
            {
                _context.Transactions.Remove(transaction);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool TransactionExists(Guid id)
        {
            return (_context.Transactions?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
