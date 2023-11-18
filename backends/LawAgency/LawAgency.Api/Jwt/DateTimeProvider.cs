namespace LawAgency.Api.Jwt;

public class DateTimeProvider : IDateTimeProvider
{
    public DateTime UtcNow => DateTime.Now;
    public DateTimeOffset UnixTimeNow => DateTimeOffset.UtcNow;
}