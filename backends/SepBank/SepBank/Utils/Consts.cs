using SepBank.Services;

namespace SepBank.Utils
{
    public class Consts
    {
        public static readonly string BankCode = Environment.GetEnvironmentVariable("CODE");
        public static string LatestBankAccount;
        public static int TimeOutMinutes = 5;
    }
}
