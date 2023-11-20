using SepBank.Utils;

namespace SepBank.Services
{
    public class AccountService
    {
        private readonly AccountNumberGenerator _accountNumberGenerator;

        public AccountService(AccountNumberGenerator accountNumberGenerator)
        {
            _accountNumberGenerator = accountNumberGenerator;
        }

        public async Task CreateNewUserAsync(string name, string personalId)
        {
            var newAccountNumber = await _accountNumberGenerator.GenerateAccountNumberAsync();
            // Use newAccountNumber to create a new user
        }
    }
}
