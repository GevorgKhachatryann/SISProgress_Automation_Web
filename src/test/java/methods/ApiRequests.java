package methods;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;


public class ApiRequests {
    public void postRequest(String endpoint){
        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int statusCode = response.statusCode(); //status
        String responseBody = response.body(); //response body
        HttpHeaders headers = response.headers(); //response header

        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);
        System.out.println("Response headers: " + headers);
    }
}
