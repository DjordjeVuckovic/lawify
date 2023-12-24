namespace SepPCC.Utils
{
    public class Consts
    {
        public static readonly Dictionary<string, string> BankIds = new Dictionary<string, string>
        {
            { "120", "http://sepbank1:80" },
            { "220", "http://sepbank2:80" }
        };

        public static readonly string IpsAPIUrl = "https://nbs.rs/QRcode/api/qr/v1/gen";
        public static readonly string QrAPIUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data={data}";
        public static readonly string QrCheckUrl = "http://api.qrserver.com/v1/read-qr-code/?fileurl={encodedUrl}";
    }
}
