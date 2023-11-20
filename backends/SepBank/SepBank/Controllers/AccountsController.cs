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
using SepBank.Utils;

namespace SepBank.Controllers
{
    public class AccountsController : Controller
    {
        private readonly ApplicationDbContext _context;

        public AccountsController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: Accounts
        public async Task<IActionResult> Index()
        {
              return _context.Accounts != null ? 
                          View(await _context.Accounts.ToListAsync()) :
                          Problem("Entity set 'ApplicationDbContext.Accounts'  is null.");
        }

        // GET: Accounts/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.Accounts == null)
            {
                return NotFound();
            }

            var account = await _context.Accounts
                .FirstOrDefaultAsync(m => m.Id == id);
            if (account == null)
            {
                return NotFound();
            }

            return View(account);
        }

        // GET: Accounts/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Accounts/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("HolderName,AccountNumber,PersonalId,ExpirationMonth,ExpirationYear,Id,RowVersion")] Account account)
        {
            if (ModelState.IsValid)
            {
                account.Id = Guid.NewGuid();
                _context.Add(account);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(account);
        }

        // GET: Accounts/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.Accounts == null)
            {
                return NotFound();
            }

            var account = await _context.Accounts.FindAsync(id);
            if (account == null)
            {
                return NotFound();
            }
            return View(account);
        }

        // POST: Accounts/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("HolderName,AccountNumber,PersonalId,ExpirationMonth,ExpirationYear,Id,RowVersion")] Account account)
        {
            if (id != account.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(account);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AccountExists(account.Id))
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
            return View(account);
        }

        // GET: Accounts/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.Accounts == null)
            {
                return NotFound();
            }

            var account = await _context.Accounts
                .FirstOrDefaultAsync(m => m.Id == id);
            if (account == null)
            {
                return NotFound();
            }

            return View(account);
        }

        // POST: Accounts/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            if (_context.Accounts == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Accounts'  is null.");
            }
            var account = await _context.Accounts.FindAsync(id);
            if (account != null)
            {
                _context.Accounts.Remove(account);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool AccountExists(Guid id)
        {
          return (_context.Accounts?.Any(e => e.Id == id)).GetValueOrDefault();
        }

        [HttpPost("CreateAccount")]
        public async Task<IActionResult> CreateAccount([FromBody] AccountCreationDTO dto)
        {
            if (dto == null)
            {
                return BadRequest("Invalid data");
            }

            // Check if PersonalId already exists
            try
            {
                var existingAccount = await _context.Accounts.FirstOrDefaultAsync(a => a.PersonalId == dto.PersonalId);

                if (existingAccount != null)
                {
                    return BadRequest("An account with this PersonalId already exists.");
                }
            } 
            catch(Exception ex)
            {
                return BadRequest("Problem checking the account.");
            }

            // Create a new account
            var newAccount = new Account(holderName: dto.Name, perosnalId: dto.PersonalId);
            var accountNumberGenerator = new AccountNumberGenerator(_context);
            newAccount.AccountNumber = await accountNumberGenerator.GenerateAccountNumberAsync();

            _context.Accounts.Add(newAccount);
            await _context.SaveChangesAsync();

            return Ok(newAccount);
        }
        
        [HttpPost("Deposit")]
        public async Task<IActionResult> Deposit([FromBody] AccountDepositDTO dto)
        {
            if (dto == null || string.IsNullOrWhiteSpace(dto.AccountNumber))
            {
                return BadRequest("Invalid data");
            }

            // Find the account by account number
            var account = await _context.Accounts.FirstOrDefaultAsync(a => a.AccountNumber == dto.AccountNumber);
            if (account == null)
            {
                return NotFound("Account not found.");
            }

            // Update the level
            account.Level += dto.Level;

            _context.Accounts.Update(account);
            await _context.SaveChangesAsync();

            return Ok(account.Level);
        }
    }
}
