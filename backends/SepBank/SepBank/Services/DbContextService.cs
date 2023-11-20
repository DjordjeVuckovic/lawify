using SepBank.Data;

namespace SepBank.Services
{
    public class DbContextService
    {
        private readonly IServiceProvider _serviceProvider;

        public DbContextService(IServiceProvider serviceProvider)
        {
            _serviceProvider = serviceProvider;
        }

        public ApplicationDbContext GetContext()
        {
            return _serviceProvider.GetRequiredService<ApplicationDbContext>();
        }
    }
}
