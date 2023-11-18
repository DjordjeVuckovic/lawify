namespace LawAgency.Api.Orders.Requests;

public class CreateOrderRequest
{
    public List<Guid> ProductsId { get; set; } = new();
    public Guid BuyerId { get; set; }
}
