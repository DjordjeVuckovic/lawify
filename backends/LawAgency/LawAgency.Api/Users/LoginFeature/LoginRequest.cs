namespace LawAgency.Api.Users.LoginFeature;

public class LoginRequest
{
    public string Email { get; set; } = null!;
    public string Password { get; set; } = null!;
}