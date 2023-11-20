using System.Text.Json;
using FluentResults;
using LawAgency.Api.Context;
using LawAgency.Api.Orders.Errors;
using LawAgency.Api.Orders.Requests;
using LawAgency.Api.Products;
using LawAgency.Api.PspConfig;
using LawAgency.Api.Users;
using MediatR;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using Microsoft.Extensions.Options;

namespace LawAgency.Api.Orders;

public static class CreateOrder
{
    public static void MapCreateOrderEndpoints(this IEndpointRouteBuilder app)
    {
        app.MapPost("/api/orders", async (CreateOrderRequest request,ISender sender) =>
        {
            var command = new CreateOrderCommand(request.ProductIds, request.BuyerId);
            var result = await sender
                .Send(command);
            return  result.IsFailed ? Results.BadRequest() : Results.Created("api/orders",result.Value);
        });
    }
    public record CreateOrderCommand(List<Guid> ProductIds,
        Guid BuyerId) : IRequest<Result<CreateOrderCommandResponse>>;

    public class CreateOrderCommandResponse 
    {
        public Guid Id { get; set; }
    }
    internal sealed class CreateOrderCommandHandler : IRequestHandler<CreateOrderCommand,Result<CreateOrderCommandResponse>>
    {
        private readonly AgencyContext _context;
        private readonly IOptions<PspSettings> _options;
        private readonly HttpClient _client;

        public CreateOrderCommandHandler(AgencyContext context, IOptions<PspSettings> options, HttpClient client)
        {
            _context = context;
            _options = options;
            _client = client;
        }

        public async Task<Result<CreateOrderCommandResponse>> Handle(CreateOrderCommand request, CancellationToken cancellationToken)
        {
            var products = await GetProductsByIds(request.ProductIds,cancellationToken);
            var buyer = await GetBuyer(request.BuyerId, cancellationToken);
            if (buyer is null) return Result.Fail(OrderErrors.BuyerNotFountError);
            var amount = CalculateAmount(products);
            var order = new Order
            {
                Products = products,
                Buyer = buyer,
                Amount = amount
            };
            var entity = await _context.Orders.AddAsync(order, cancellationToken);
            await _context.SaveChangesAsync(cancellationToken);
            var requestContent = CreateJsonRequest(entity.Entity);
            _client.DefaultRequestHeaders.Add("X-API-KEY", _options.Value.ApiKey);
            var response = await _client.PostAsync(_options.Value.PspUrl, requestContent, cancellationToken);
            if (response.IsSuccessStatusCode)
            {
                //TODO success     
            }
           
            return new CreateOrderCommandResponse
            {
                Id = entity.Entity.Id
            };
        }

        private StringContent CreateJsonRequest(Order entity)
        {
            var requestPayload = new
            {
                merchantUsername = _options.Value.MerchantEmail,
                orderId = entity.Id,
                amount = entity.Amount
            };
            var jsonPayload = JsonSerializer.Serialize(requestPayload);
            return new StringContent(jsonPayload, System.Text.Encoding.UTF8, "application/json");
        }

        private double CalculateAmount(List<Product> products)
        {
            var amount = 0.0;
            products.ForEach(x =>
            {
                amount += x.Amount;
            });
            return amount;
        }

        private async Task<User?> GetBuyer(Guid buyerId, CancellationToken cancellationToken)
        {
            var user = await _context.Users
                .SingleOrDefaultAsync(x => x.Id == buyerId,
                    cancellationToken: cancellationToken);
            return user;
        }

        private async Task<List<Product>> GetProductsByIds(List<Guid> productIds, CancellationToken cancellationToken)
        {
            var products = await _context.Products
                .Where(x => productIds.Contains(x.Id))
                .ToListAsync(cancellationToken);
            return products;
        }
    }
}