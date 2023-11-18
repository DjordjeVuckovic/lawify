using FluentResults;
using LawAgency.Api.Context;
using LawAgency.Api.Users.Hashes;
using LawAgency.Api.Users.Requests;
using MediatR;

namespace LawAgency.Api.Users.CreateUserFeature;

public static class CreateUser
{
    public static void MapCreateUserEndpoints(this IEndpointRouteBuilder app)
    {
        app.MapPost("/api/users", async (CreateUserRequest request, ISender sender) =>
        {
            var command = new CreateUserCommand(request.Email, request.Password);
            var result = await sender
                .Send(command);
            return result.IsFailed ? 
                Results.BadRequest() 
                : Results.Created("api/users",result.Value);
        });
    }

    public record CreateUserCommand(string Email,
        string Password) : IRequest<Result<CreateUserCommandResponse>>;
    public class CreateUserCommandResponse
    {
        public Guid Id { get; set; }
    }
    internal sealed class CreteUserCommandHandler : IRequestHandler<CreateUserCommand,Result<CreateUserCommandResponse>>
    {
        private readonly AgencyContext _context;

        public CreteUserCommandHandler(AgencyContext context)
        {
            _context = context;
        }

        public async Task<Result<CreateUserCommandResponse>> Handle(CreateUserCommand request, CancellationToken cancellationToken)
        {
            var hashedPassword = Hash.HashPassword(request.Password);
            var user = new User
            {
                Email = request.Email,
                Password = hashedPassword
            };
            var entity = await _context.Users.AddAsync(user, cancellationToken);
            await _context.SaveChangesAsync(cancellationToken);
            return new CreateUserCommandResponse
            {
                Id = entity.Entity.Id
            };
        }
    }
}