using Microsoft.EntityFrameworkCore;

namespace LawAgency.Api;

public static class RunMigrationsExtension
{
    public static void MigrateDb<TContext>(this WebApplication app) 
        where TContext : DbContext
    {
        using var scope = app.Services.CreateScope();
        var services = scope.ServiceProvider;
        try
        {
            var context = services.GetRequiredService<TContext>();
            context.Database.Migrate();
        }
        catch (Exception ex)
        {

        }
    }
}