import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ChatGPTClient {

    public static String chatGPT(String prompt) throws URISyntaxException {

        String urlString  = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"Hi GPT! Today I would like you to make lyrics for" +
                    "a possible song according to my mood. Make it catchy, rhyme and artistic. My mood can be" +
                    "described in the following words\"}," +
                    "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                    "]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractContentFromResponse(response.toString());

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}