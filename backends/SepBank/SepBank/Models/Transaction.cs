namespace SepBank.Models
{
    public class Transaction : BaseModel
    {
        public Guid? ReceiverId { get; set; }
        public string? ReceiverAccountNumber { get; set; }
        public Guid SenderId { get; set; }
        public double Amount { get; set; }
        public bool IsConfirmed { get; set; }
    }
}
