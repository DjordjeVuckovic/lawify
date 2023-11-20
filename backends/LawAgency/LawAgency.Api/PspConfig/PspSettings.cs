namespace LawAgency.Api.PspConfig;

public class PspSettings
{
    public const string SectionName = "PspSettings";
    public string ApiKey { get; init; } = null!;
    public string MerchantEmail { get; init; } = null!;
}