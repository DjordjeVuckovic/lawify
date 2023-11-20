using FluentResults;
using LawAgency.Api.Context;
using LawAgency.Api.Orders.Errors;
using LawAgency.Api.Orders.Requests;
using LawAgency.Api.Products;
using LawAgency.Api.PspConfig;
using LawAgency.Api.Users;
using MediatR;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;

namespace LawAgency.Api.Orders;

public static class CreateOrder
{
    public static void MapCreateOrderEndpoints(this IEndpointRouteBuilder app)
    {
        app.MapPost("/api/orders", async (CreateOrderRequest request,ISender sender) =>
        {
            var command = new CreateOrderCommand(request.ProductsId, request.BuyerId);
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

        public CreateOrderCommandHandler(AgencyContext context, IOptions<PspSettings> options)
        {
            _context = context;
            _options = options;
        }

        public async Task<Result<CreateOrderCommandResponse>> Handle(CreateOrderCommand request, CancellationToken cancellationToken)
        {
            var products = await GetProductsByIds(request.ProductIds,cancellationToken);
            var buyer = await GetBuyer(request.BuyerId, cancellationToken);
            if (buyer is null) return Result.Fail(OrderErrors.BuyerNotFountError);
            var order = new Order
            {
                Products = products,
                Buyer = buyer
            };
            var entity = await _context.Orders.AddAsync(order, cancellationToken);
            await _context.SaveChangesAsync(cancellationToken);
            return new CreateOrderCommandResponse
            {
                Id = entity.Entity.Id
            };
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
            return await _context.Products
                .Where(x => productIds.Contains(x.Id))
                .ToListAsync(cancellationToken);
        }
    }
}