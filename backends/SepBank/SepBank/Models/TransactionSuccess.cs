namespace SepBank.Models
{
    public class TransactionSuccess : BaseModel
    {
        public Guid TransactionID { get; set; }
        public bool IsSuccess { get; set; } = false;
    }
}
