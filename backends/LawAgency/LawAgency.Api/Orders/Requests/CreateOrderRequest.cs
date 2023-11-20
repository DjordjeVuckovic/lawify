namespace LawAgency.Api.Orders.Requests;

public class CreateOrderRequest
{
    public List<Guid> ProductIds { get; set; } = new();
    public Guid BuyerId { get; set; }
}
