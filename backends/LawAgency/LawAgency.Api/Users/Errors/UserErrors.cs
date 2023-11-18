using FluentResults;

namespace LawAgency.Api.Users.Errors;

public static class UserErrors
{
    public static IError InvalidCredentialsError => new Error("Invalid credentials!");
    public static IError CannotGenerateToken => new Error("Can not generate token!");
}