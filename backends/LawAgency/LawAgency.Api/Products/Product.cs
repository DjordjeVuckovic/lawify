namespace LawAgency.Api.Products;

public class Product
{
    public Guid Id { get; set; }
    public string Name { get; set; } = null!;
    public float Amount { get; set; }
    public string Currency { get; set; } = null!;
}