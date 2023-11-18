using LawAgency.Api.Products;
using LawAgency.Api.Users;

namespace LawAgency.Api.Orders;

public class Order
{
    public Guid Id { get; set; }
    public List<Product> Products { get; set; } = new();
    public User Buyer { get; set; } = null!;
}