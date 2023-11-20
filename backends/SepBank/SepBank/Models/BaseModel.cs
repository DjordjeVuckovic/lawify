using System.ComponentModel.DataAnnotations;

namespace SepBank.Models
{
    public class BaseModel
    {
        [Key]
        public Guid Id { get; set; }
        [Timestamp]
        public byte[] RowVersion { get; set; }
        public DateTime CreationDate { get; } = DateTime.Now;
    }
}
