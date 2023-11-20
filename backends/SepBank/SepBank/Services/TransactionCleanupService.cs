namespace SepBank.Services
{
    public class TransactionCleanupService : BackgroundService
    {
        private readonly TimeSpan _cleanupInterval = TimeSpan.FromMinutes(1); // Adjust as needed
        private readonly Dictionary<Guid, (DateTime Timestamp, Guid ReceiverId, double Amount)> _transactionRequests;

        public TransactionCleanupService(Dictionary<Guid, (DateTime Timestamp, Guid ReceiverId, double Amount)> transactionRequests)
        {
            _transactionRequests = transactionRequests;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            while (!stoppingToken.IsCancellationRequested)
            {
                CleanUpExpiredTransactions();
                await Task.Delay(_cleanupInterval, stoppingToken);
            }
        }

        private void CleanUpExpiredTransactions()
        {
            var currentTime = DateTime.UtcNow;
            var keysToRemove = _transactionRequests.Where(kvp => (currentTime - kvp.Value.Timestamp).TotalMinutes > 5)
                                                   .Select(kvp => kvp.Key)
                                                   .ToList();

            foreach (var key in keysToRemove)
            {
                _transactionRequests.Remove(key);
            }
        }
    }
}
