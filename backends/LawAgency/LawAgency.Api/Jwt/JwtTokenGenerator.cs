using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using LawAgency.Api.Users;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;

namespace LawAgency.Api.Jwt;

public class JwtTokenGenerator : IJwtTokenGenerator
{
    private readonly IDateTimeProvider _timeProvider;
    private readonly JwtSettings _jwtSettings;

    public JwtTokenGenerator(IDateTimeProvider timeProvider, IOptions<JwtSettings> jwtSettings)
    {
        _timeProvider = timeProvider;
        _jwtSettings = jwtSettings.Value;
    }

    public string GenerateToken(User user)
    {
        var signingCredentials = new SigningCredentials(new SymmetricSecurityKey(
            Encoding.UTF8.GetBytes(_jwtSettings.Secret)
        ),SecurityAlgorithms.HmacSha256);
        var securityToken = new JwtSecurityToken(
            issuer: _jwtSettings.Issuer,
            audience: _jwtSettings.Audience,
            expires: _timeProvider.UtcNow.AddMinutes(_jwtSettings.ExpiryMinutes),
            notBefore: _timeProvider.UtcNow.AddSeconds(1),
            claims: GetClaims(user),
            signingCredentials: signingCredentials);
        return new JwtSecurityTokenHandler().WriteToken(securityToken);
    }
    private IEnumerable<Claim> GetClaims(User user)
    {
        var claims = new List<Claim>
        {
            new(JwtRegisteredClaimNames.Sub, user.Id.ToString()),
            new(JwtRegisteredClaimNames.Email, user.Email),
            new(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
            new(JwtRegisteredClaimNames.Typ, "Bearer"),
            new(JwtRegisteredClaimNames.Iat,
                _timeProvider.UnixTimeNow.ToUnixTimeSeconds().ToString())
        };
        return claims.ToArray();
    }
}