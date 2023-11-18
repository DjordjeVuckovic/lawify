using LawAgency.Api.Orders;
using LawAgency.Api.Products;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace LawAgency.Api.Context.Configurations;

public class OrderConfiguration : IEntityTypeConfiguration<Order>
{
    public void Configure(EntityTypeBuilder<Order> builder)
    {
        builder.HasMany(x => x.Products)
            .WithMany();
        /*builder.HasMany(e => e.Products)
            .WithMany()
            .UsingEntity(
                "OrderProduct",
                l => l.HasOne(typeof(Product)).WithMany().HasForeignKey("ProductId").HasPrincipalKey(nameof(Product.Id)),
                r => r.HasOne(typeof(Order)).WithMany().HasForeignKey("OrderId").HasPrincipalKey(nameof(Order.Id)),
                j => j.HasKey("ProductId", "OrderId"));*/
    }
}