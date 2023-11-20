using SepBank.Utils;

namespace SepBank.Models
{
    public class Account : BaseModel
    {
        public Account() { }
        public Account(string holderName, Guid perosnalId)
        {
            HolderName = holderName;
            PersonalId = perosnalId;
            SetExpirationDate();
            SetSecurityCode();
        }

        public string HolderName { get; set; }
        public string AccountNumber { get; set; }
        public double Level { get; set; } = 0;
        public Guid PersonalId { get; set; } //JMBG mock number
        public int SecurityNumber { get; private set; }
        public string ExpirationMonth { get; private set; }
        public string ExpirationYear { get; private set; }

        private void SetExpirationDate()
        {
            var currentDate = DateTime.Now;
            var expirationDate = currentDate.AddYears(5);

            this.ExpirationMonth = expirationDate.Month.ToString("00");
            this.ExpirationYear = expirationDate.ToString("yy");
        }

        private void SetSecurityCode()
        {
            Random random = new();
            SecurityNumber = random.Next(100, 1000);
        }
    }
}
