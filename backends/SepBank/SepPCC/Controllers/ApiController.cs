using Microsoft.AspNetCore.Mvc;
using SepPCC.Utils;
using System.Text.Json;
using System.Text;

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
        public string SenderAccountNumber { get; set; }
        public string ReceiverAccountNumber { get; set; }
        public double Amount { get; set; }
    }
}