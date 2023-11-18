using System.Security.Cryptography;
using System.Text;

namespace LawAgency.Api.Users.Hashes;

public class Hash
{
    private const int KeySize = 64;
    private const int Iterations = 350000;
    private const string Salt = "Ivana salt";
    private static readonly HashAlgorithmName  HashAlgorithm = HashAlgorithmName.SHA512;
    public static string HashPassword(string password)
    {
        var salt = Encoding.UTF8.GetBytes(Salt);
        var hash = Rfc2898DeriveBytes.Pbkdf2(
            Encoding.UTF8.GetBytes(password),
            salt,
            Iterations,
            HashAlgorithm,
            KeySize);
        return Convert.ToHexString(hash);
    }
}