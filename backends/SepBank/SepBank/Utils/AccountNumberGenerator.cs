using Microsoft.EntityFrameworkCore;
using SepBank.Data;

namespace SepBank.Utils
{
    public class AccountNumberGenerator
    {
        private readonly ApplicationDbContext _context;

        public AccountNumberGenerator(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<string> GenerateAccountNumberAsync()
        {
            var nextNumber = await GetNextNumberAsync();
            var accountNumberWithoutControlDigits = Consts.BankCode + nextNumber.ToString().PadLeft(6, '0');
            var controlDigit1 = CalculateControlDigit(accountNumberWithoutControlDigits, 2);
            var controlDigit2 = CalculateControlDigit(accountNumberWithoutControlDigits, 3);
            return accountNumberWithoutControlDigits + controlDigit1 + controlDigit2;
        }

        private async Task<int> GetNextNumberAsync()
        {
            // Assuming you have a User or Account entity that stores account numbers
            var lastAccount = await _context.Accounts
                .OrderByDescending(u => u.AccountNumber)
                .FirstOrDefaultAsync();

            if (lastAccount != null)
            {
                // Extract the middle part and increment it
                if (int.TryParse(lastAccount.AccountNumber.Substring(3, 6), out var lastNumber))
                {
                    return lastNumber + 1;
                }
            }

            return 1; // Start from 1 if no accounts are found
        }

        private int CalculateControlDigit(string number, int divisor)
        {
            var sum = number.Sum(c => c - '0'); // Convert each char to int and sum
            return sum % divisor;
        }
    }
}
