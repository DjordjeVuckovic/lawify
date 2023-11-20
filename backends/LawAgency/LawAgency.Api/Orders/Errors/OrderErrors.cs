using FluentResults;

namespace LawAgency.Api.Orders.Errors;

public static class OrderErrors
{
    public static IError BuyerNotFountError => new Error("Buyer not found!");
    public static IError CreatingTransactionFailed => new Error("Transaction failed!");
}