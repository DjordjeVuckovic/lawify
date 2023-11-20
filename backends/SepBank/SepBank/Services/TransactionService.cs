using Microsoft.EntityFrameworkCore;
using SepBank.Data;
using SepBank.Models.DTO;
using SepBank.Utils;
using System.Text;
using System.Text.Json;

namespace SepBank.Services
{
    public class TransactionService
    {
        private readonly ApplicationDbContext _context;

        public TransactionService(ApplicationDbContext context)
        {
            _context = context;
        }

        private bool IsSenderFromThisBank(string accountNumber)
            => accountNumber.Substring(0, Consts.BankCode.Length) != Consts.BankCode.Length.ToString();

        public (bool, Guid) IsSenderLevelSufficient(string accountNumber, double amount)
        {
            if(IsSenderFromThisBank(accountNumber))
            {
                // Payer is from a different bank

            }

            var account = _context.Accounts.FirstOrDefault(a => a.AccountNumber == accountNumber);
            if (account == null)
                return (false, Guid.Empty);

            return (account.Level >= amount, account.Id);
        }

        public (bool, Guid) IsSenderLevelSufficientPCC(string accountNumber, double amount)
        {
            var account = _context.Accounts.FirstOrDefault(a => a.AccountNumber == accountNumber);
            if (account == null)
                return (false, Guid.Empty);

            return (account.Level >= amount, account.Id);
        }

        public async Task<bool> RequestPPCTransaction(MakeTransactionDTO dto)
        {
            var client = new HttpClient();
            var receiverAccountNumber = _context.Accounts.Find(dto.ReceiverId).AccountNumber;

            var paymentRequest = new PCCTransactionDTO
            {
                SenderAccountNumber = dto.SenderAccountNumber,
                Amount = dto.Amount,
                ReceiverAccountNumber = receiverAccountNumber
            };

            var json = JsonSerializer.Serialize(paymentRequest);
            var data = new StringContent(json, Encoding.UTF8, "application/json");

            var cts = new CancellationTokenSource(TimeSpan.FromSeconds(60));
            var response = await client.PostAsync("http://seppcc:80/Api/PayFromSenderAccount", data, cts.Token);

            return response.IsSuccessStatusCode;
        }

        public async Task<bool> TryMakeTransactionAsync(MakeTransactionDTO dto)
        {
            if(!dto.SenderAccountNumber.StartsWith(Consts.BankCode))
            {
                return await RequestPPCTransaction(dto);
            }

            // We are using optimistic concurrency to be sure account details won't change
            using (var transaction = await _context.Database.BeginTransactionAsync())
            {
                try
                {
                    var senderAccount = await _context.Accounts.FirstOrDefaultAsync(a => a.AccountNumber == dto.SenderAccountNumber);
                    if (senderAccount == null || senderAccount.Level < dto.Amount)
                    {
                        return false; // Insufficient funds or account not found
                    }

                    // Deduct amount from sender's account
                    senderAccount.Level -= dto.Amount;

                    // Add amount to receiver's account
                    var receiverAccount = await _context.Accounts.FirstOrDefaultAsync(a => a.Id == dto.ReceiverId);

                    if(receiverAccount == null)
                    {
                        return false;
                    }

                    receiverAccount.Level += dto.Amount;

                    // Create and save the transaction
                    var transactionRecord = new Models.Transaction
                    {
                        ReceiverId = dto.ReceiverId,
                        SenderId = senderAccount.Id,
                        Amount = dto.Amount,
                        IsConfirmed = true
                    };

                    _context.Transactions.Add(transactionRecord);
                    await _context.SaveChangesAsync();

                    await transaction.CommitAsync();
                    return true;
                }
                catch(Exception ex)
                {
                    await transaction.RollbackAsync();
                    return false;
                }
            }
        }
    }
}
