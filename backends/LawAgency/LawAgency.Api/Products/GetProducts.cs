using LawAgency.Api.Context;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace LawAgency.Api.Products;

public static class GetProducts
{
    public static void MapGetProductEndpoints(this IEndpointRouteBuilder app)
    {
        app.MapGet("/api/products", async (ISender sender) =>
        {
            var result = await sender
                .Send(new GetProductsQuery());
            return Results.Ok(result);
        });
    }
    
    public record GetProductsQuery : IRequest<List<GetProductsQueryResponse>>;
    public class GetProductsQueryResponse
    {
        public Guid Id { get; set; }
        public string Name { get; set; } = null!;
        public float Amount { get; set; }
        public string Currency { get; set; } = null!;
    }
    internal sealed class GetProductsHandler :IRequestHandler<GetProductsQuery,List<GetProductsQueryResponse>>
    {
        private readonly AgencyContext _context;

        public GetProductsHandler(AgencyContext context)
        {
            _context = context;
        }

        public async Task<List<GetProductsQueryResponse>> Handle(GetProductsQuery request, CancellationToken cancellationToken)
        {
            var products = await _context.Products
                .ToListAsync(cancellationToken);
            var result = products
                .Select(x => new GetProductsQueryResponse
            {
                Id = x.Id,
                Amount = x.Amount,
                Currency = x.Currency,
                Name = x.Name
            }).ToList();
            return result;
        }
    }
}