import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AI{
    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_ENDPOINT = "https://api.openai.com/v1/engines/davinci/completions";

    public static void main(String[] args) {
        try {
            // Четене на входен текст от потребителя
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Въведете текст: ");
            String userInput = reader.readLine();

            // Изпращане на заявка към OpenAI API
            String response = sendOpenAIRequest(userInput);

            // Показване на отговора от бота
            System.out.println("Бот: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String sendOpenAIRequest(String inputText) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(OPENAI_API_ENDPOINT);

        // Заглавие и тяло на заявката
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String requestBody = "{\"prompt\": \"" + inputText + "\", \"max_tokens\": 100}";
        httpPost.setEntity(new StringEntity(requestBody));

        // Изпращане на заявка и обработване на отговора
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }
}
