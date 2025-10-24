import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class AnaliseSentimento {
    public static void main(String[] args) {
        String endpoint = System.getenv("AZURE_ENDPOINT");
        String apiKey = System.getenv("AZURE_API_KEY");

        if (endpoint == null || apiKey == null) {
            System.err.println("❌ Erro: Variáveis de ambiente AZURE_ENDPOINT e AZURE_API_KEY não estão configuradas.");
            System.err.println("Configure-as antes de executar o programa.");
            System.exit(1);
        }

        try {
            HttpClient client = HttpClient.newHttpClient();

            String texto = "Eu estou muito feliz com o resultado do projeto!";

            String json = "{ \"kind\": \"SentimentAnalysis\", \"parameters\": { \"modelVersion\": \"latest\" }, "
                        + "\"analysisInput\": { \"documents\": [ { \"id\": \"1\", \"language\": \"pt\", \"text\": \"" 
                        + texto + "\" } ] } }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "/language/:analyze-text?api-version=2023-04-01"))
                    .header("Content-Type", "application/json")
                    .header("Ocp-Apim-Subscription-Key", apiKey)
                    .POST(BodyPublishers.ofString(json))
                    .build();

            
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            System.out.println("✅ Resposta da API:");
            System.out.println(response.body());

        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com o serviço Azure AI Language:");
            e.printStackTrace();
        }
    }
}

