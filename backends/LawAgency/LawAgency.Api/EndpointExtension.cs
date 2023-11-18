using LawAgency.Api.Orders;
using LawAgency.Api.Products;
using LawAgency.Api.Users;
using LawAgency.Api.Users.CreateUserFeature;
using LawAgency.Api.Users.LoginFeature;

namespace LawAgency.Api
{
    public static class EndpointExtension
    {
        public static IEndpointRouteBuilder MapEndpoints(this IEndpointRouteBuilder application) 
        {
            application.MapGetProductEndpoints();
            application.MapCreateUserEndpoints();
            application.MapCreateOrderEndpoints();
            application.MapAuthEndpoints();
            return application;
        }
    }
}
