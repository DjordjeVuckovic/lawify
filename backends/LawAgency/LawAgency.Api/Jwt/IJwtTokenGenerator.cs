using LawAgency.Api.Users;

namespace LawAgency.Api.Jwt;

public interface IJwtTokenGenerator
{
    string GenerateToken(User user);
}