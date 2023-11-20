namespace SepBank.Models.DTO
{
    public class MakeTransactionDTO
    {
        public Guid ReceiverId { get; set; }
        public string SenderAccountNumber { get; set; }
        public string SenderExpirationMonth { get; set; }
        public string SenderExpirationYear { get; set; }
        public int SenderSecurityNumber { get; set; }
        public double Amount { get; set; }
        public DateTime RequestStart { get; set; }
    }
}
