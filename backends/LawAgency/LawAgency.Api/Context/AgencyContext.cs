using System.Reflection;
using LawAgency.Api.Orders;
using LawAgency.Api.Products;
using LawAgency.Api.Users;
using Microsoft.EntityFrameworkCore;

namespace LawAgency.Api.Context;

public class AgencyContext : DbContext
{
    public DbSet<Product> Products { get; set; } = null!;
    public DbSet<Order> Orders { get; set; } = null!;
    public DbSet<User> Users { get; set; } = null!;

    public AgencyContext(DbContextOptions<AgencyContext> options)
        : base(options)
    {
        
    }
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());

        /*modelBuilder.Entity<Order>()
            .HasMany(e => e.Products)
            .WithMany();*/
    }
}