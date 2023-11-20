using Microsoft.EntityFrameworkCore;
using SepBank.Models;

namespace SepBank.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public DbSet<Account> Accounts{ get; set; }

        public DbSet<SepBank.Models.Transaction>? Transactions { get; set; }
    }
}