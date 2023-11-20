namespace SepBank.Models.DTO
{
    public class PCCTransactionDTO
    {
        public string SenderAccountNumber { get; set; }
        public string ReceiverAccountNumber { get; set; }
        public double Amount { get; set; }
    }
}
