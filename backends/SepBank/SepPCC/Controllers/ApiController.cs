using Microsoft.AspNetCore.Mvc;
using SepPCC.Utils;
using System.Text.Json;
using System.Text;
using System.Globalization;
using System.Reflection;
using ZXing;
using System.Drawing;

namespace SepPCC.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ApiController : ControllerBase
    {
        private readonly ILogger<ApiController> _logger;

        public ApiController(ILogger<ApiController> logger)
        {
            _logger = logger;
        }

        [HttpGet("GetIPSQR")]
        public async Task<IActionResult> PayFromSenderAccountQR(string receiverAccountNumber, double paymentAmount)
        {
            var receiverBankId = receiverAccountNumber.Substring(0, 3);

            string url = Consts.BankIds[receiverBankId];
            string apiUrl = $"{url}/GetAccountData?accountNumber={receiverAccountNumber}";

            using (var client = new HttpClient())
            {
                try
                {
                    HttpResponseMessage response = await client.GetAsync(apiUrl);
                    response.EnsureSuccessStatusCode();
                    string responseBody = await response.Content.ReadAsStringAsync();

                    string[] values = responseBody.Split(';');

                    if (values.Length == 2)
                    {
                        (string accountNumber, string accountHolderName) = (values[0], values[1]);

                        var ipsData = new IPSGenerator(accountNumber, accountHolderName, paymentAmount);

                        var serialized = ipsData.Serialize();
                        HttpResponseMessage qrResponse = await client.GetAsync(Consts.QrAPIUrl.Replace("{data}", serialized));

                        // Ensure the request was successful
                        if (response.IsSuccessStatusCode)
                        {
                            // Read the response content as a byte array
                            byte[] imageBytes = await qrResponse.Content.ReadAsByteArrayAsync();

                            // Assuming the image is of a specific format, e.g., "image/png"
                            return File(imageBytes, "image/png");
                        }
                        else
                        {
                            // Handle the error scenario
                            return StatusCode((int)qrResponse.StatusCode, "Error occurred while making the GET request for QR.");
                        }
                    }

                    return BadRequest($"Problem reading data for account number: {receiverAccountNumber}");
                }
                catch (HttpRequestException e)
                {
                    // Handle different types of exceptions accordingly
                    return BadRequest($"Request Exception: {e.Message}");
                }
            }
        }

        [HttpPost("PayFromSenderAccount")]
        public async Task<IActionResult> PayFromSenderAccount([FromBody] PaymentRequest paymentRequest)
        {
            var senderBankId = paymentRequest.SenderAccountNumber.Substring(0, 3);           

            if (Consts.BankIds.ContainsKey(senderBankId))
            {
                string url = Consts.BankIds[senderBankId];
                string apiUrl = $"{url}/MakePCCTransaction";

                PaymentRequest transaction = new PaymentRequest
                {
                    SenderAccountNumber = paymentRequest.SenderAccountNumber,
                    ReceiverAccountNumber = paymentRequest.ReceiverAccountNumber,
                    Amount = paymentRequest.Amount
                };

                var json = JsonSerializer.Serialize(transaction);
                var data = new StringContent(json, Encoding.UTF8, "application/json");

                using (var client = new HttpClient())
                {
                    try
                    {
                        HttpResponseMessage response = await client.PostAsync(apiUrl, data);
                        response.EnsureSuccessStatusCode();
                        string responseBody = await response.Content.ReadAsStringAsync();

                        return Ok(responseBody);
                    }
                    catch (HttpRequestException e)
                    {
                        // Handle different types of exceptions accordingly
                        return BadRequest($"Request Exception: {e.Message}");
                    }
                }
            }
            else
            {
                return BadRequest("Key not found in the dictionary.");
            }
        }
    }

    public class PaymentRequest
    {
        public string SenderAccountNumber { get; set; } = "";
        public string ReceiverAccountNumber { get; set; }
        public double Amount { get; set; }
    }

    public class IPSGenerator
    {
        public string K { get; set; } = "PR";
        public string V { get; set; } = "01";
        public string C { get; set; } = "1";
        public string R { get; set; }
        public string N { get; set; }
        public string I { get; set; } = "RSD{Amount}";
        public string P { get; set; } = "Podaci posiljaoca";
        public string SF { get; set; } = "189";
        public string S { get; set; } = "Transakcija po nalogu gradjana";
        public string RO { get; set; } = "";

        public IPSGenerator() { }

        public IPSGenerator(string receiverAccountNumber, string receiverAddress, double amount)
        {
            R = PadWithZeros(receiverAccountNumber);
            N = receiverAddress;
            I = I.Replace("{Amount}", DoubleToStringWithComma(amount));
        }

        private string PadWithZeros(string input)
        {
            if (input == null)
            {
                throw new ArgumentNullException(nameof(input));
            }

            // Check if the input length is already 18 or more
            if (input.Length >= 18)
            {
                return input;
            }

            // Calculate how many zeros are needed
            int zerosNeeded = 18 - input.Length;

            // Insert zeros after the third character
            return input.Insert(3, new string('0', zerosNeeded));
        }

        private static string DoubleToStringWithComma(double value)
        {
            // Create a NumberFormatInfo object with a comma as the decimal separator
            NumberFormatInfo nfi = new NumberFormatInfo
            {
                NumberDecimalSeparator = ",",
                NumberDecimalDigits = 2
            };

            // Convert the double to a string with two decimal places using the custom format
            return value.ToString("F", nfi);
        }


        public string Serialize()
        {
            StringBuilder sb = new StringBuilder();
            Type type = this.GetType();

            foreach (PropertyInfo prop in type.GetProperties())
            {
                if (sb.Length > 0)
                {
                    sb.Append("|");
                }

                object value = prop.GetValue(this);
                sb.Append($"{prop.Name}:{value}");
            }

            return sb.ToString();
        }
    }
}