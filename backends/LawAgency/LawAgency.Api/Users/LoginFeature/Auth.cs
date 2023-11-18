using FluentResults;
using LawAgency.Api.Context;
using LawAgency.Api.Jwt;
using LawAgency.Api.Users.Errors;
using LawAgency.Api.Users.Hashes;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace LawAgency.Api.Users.LoginFeature;

public static class Auth
{
    public static void MapAuthEndpoints(this IEndpointRouteBuilder app)
    {
        app.MapPost("/api/users/auth", async (LoginRequest request,ISender sender) =>
        {
            var command = new LoginCommand(request.Email, request.Password);
            var result = await sender.Send(command);
            return result.IsFailed ?
                Results.BadRequest()
                : Results.Ok(result.Value);
        });
    }

    public record LoginCommand(string Email,
        string Password) : IRequest<Result<LoginCommandResponse>>;
    public class LoginCommandResponse
    {
        public string Token { get; set; } = null!;
    }
    internal sealed class LoginCommandHandler : IRequestHandler<LoginCommand,Result<LoginCommandResponse>>
    {
        private readonly AgencyContext _context;
        private readonly IJwtTokenGenerator _jwtTokenGenerator;

        public LoginCommandHandler(AgencyContext context, IJwtTokenGenerator jwtTokenGenerator)
        {
            _context = context;
            _jwtTokenGenerator = jwtTokenGenerator;
        }

        public async Task<Result<LoginCommandResponse>> Handle(LoginCommand request, CancellationToken cancellationToken)
        {
            var user = await GetUserByEmail(request.Email);
            if (user is null) return Result.Fail(UserErrors.InvalidCredentialsError);
            var validPassword = CheckPasswords(request.Password, user.Password);
            if (!validPassword) return Result.Fail(UserErrors.InvalidCredentialsError);
            try
            {
                var token = _jwtTokenGenerator.GenerateToken(user);
                return new LoginCommandResponse
                {
                    Token = token
                };
            }
            catch (Exception)
            {
                return Result.Fail(UserErrors.CannotGenerateToken); 
            }
        }

        private static bool CheckPasswords(string requestPassword, string userPassword)
        {
            var hashedRequest = Hash.HashPassword(requestPassword);
            return hashedRequest == userPassword;
        }

        private async Task<User?> GetUserByEmail(string requestEmail)
        {
            return await _context.Users.FirstOrDefaultAsync(x => x.Email == requestEmail);
        }
    }
}