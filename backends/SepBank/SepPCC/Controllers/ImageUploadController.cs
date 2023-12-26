using CloudinaryDotNet;
using CloudinaryDotNet.Actions;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
using SepPCC.Utils;
using System.Web;

namespace SepPCC.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ImageUploadController : ControllerBase
    {
        private readonly Cloudinary _cloudinary;

        public ImageUploadController()
        {
            var (cloudName, apiKey, apiSecret) = ReadCloudinaryConfig("./cloudinary.csv");

            Account account = new Account(cloudName, apiKey, apiSecret);
            _cloudinary = new Cloudinary(account);
            _cloudinary.Api.Secure = true;
        }

        private (string CloudName, string ApiKey, string ApiSecret) ReadCloudinaryConfig(string filePath)
        {
            // string[] lines = System.IO.File.ReadAllLines(filePath);
            //
            // // Assuming the first line is the header and the second line contains the data
            // if (lines.Length < 2)
            //     throw new InvalidOperationException("Invalid configuration file.");

            var data = "dcamdqqyw,475364442376198,ZDW6T_-Iyjutxm_TtGmwqTx00GE".Split(",");
            if (data.Length != 3)
                throw new InvalidOperationException("Configuration data is not valid.");

            return (data[0], data[1], data[2]);
        }

        [HttpPost]
        public async Task<IActionResult> ValidateQR(IFormFile image)
        {
            // Upload image to cloudinary

            if (image == null || image.Length == 0)
                return BadRequest("No image provided");

            var uploadParams = new ImageUploadParams()
            {
                File = new FileDescription(image.FileName, image.OpenReadStream())
            };

            var uploadResult = await _cloudinary.UploadAsync(uploadParams);

            if (uploadResult.Error != null)
                return BadRequest(uploadResult.Error.Message);

            // Scan QR code from image
            string qrImageUrl = uploadResult.SecureUrl.ToString();
            string encodedUrl = HttpUtility.UrlEncode(qrImageUrl);

            using (var client = new HttpClient())
            {
                HttpResponseMessage response = await client.GetAsync(Consts.QrCheckUrl.Replace("{encodedUrl}", encodedUrl));
                string jsonResponse = await response.Content.ReadAsStringAsync();

                // Check if QR code from image is valid
                IPSGenerator ipsGenerator = DeserializeIPSGenerator(jsonResponse);

                if (ipsGenerator == null)
                {
                    return BadRequest("QR code not formatted well.");
                }

                // Create transaction request
                var receiverBankId = ipsGenerator.R.Substring(0, 3);

                string url = Consts.BankIds[receiverBankId];
                string apiUrl = $"{url}/RequestTransactionForAccountNumber?accountNumber={ipsGenerator.R}&amount={ipsGenerator.I.Substring(3).Replace(',', '.')}";

                try
                {
                    response = await client.GetAsync(apiUrl);
                    response.EnsureSuccessStatusCode();
                    string responseBody = await response.Content.ReadAsStringAsync();
                    return Ok(responseBody);
                }
                catch (Exception ex)
                {
                    return BadRequest(response.Content.ReadAsStringAsync());
                }
            }
        }

        private static IPSGenerator DeserializeIPSGenerator(string jsonString)
        {
            var jObject = JArray.Parse(jsonString)[0]["symbol"][0]["data"].ToString();
            var keyValuePairs = jObject.Split('|');

            var ipsGenerator = new IPSGenerator();
            foreach (var pair in keyValuePairs)
            {
                var keyValue = pair.Split(':');
                if (keyValue.Length == 2)
                {
                    switch (keyValue[0])
                    {
                        case "K": ipsGenerator.K = keyValue[1]; break;
                        case "V": ipsGenerator.V = keyValue[1]; break;
                        case "C": ipsGenerator.C = keyValue[1]; break;
                        case "R": ipsGenerator.R = keyValue[1]; break;
                        case "N": ipsGenerator.N = keyValue[1]; break;
                        case "I": ipsGenerator.I = keyValue[1]; break;
                        case "P": ipsGenerator.P = keyValue[1]; break;
                        case "SF": ipsGenerator.SF = keyValue[1]; break;
                        case "S": ipsGenerator.S = keyValue[1]; break;
                        case "RO": ipsGenerator.RO = keyValue[1]; break;
                        default:
                            return null;
                    }
                }
            }
            return ipsGenerator;
        }
    }
}
