namespace LawAgency.Api.Jwt;

public interface IDateTimeProvider
{
    DateTime UtcNow { get; }
    DateTimeOffset UnixTimeNow { get; }
}