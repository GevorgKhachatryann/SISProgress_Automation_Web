package methods;

import Config.URL;
import DTO.UserDTO;
import Locators.RegistrationLocators;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.time.Duration;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApiRequests {
    private final WebDriver driver;
    public static WebDriverWait wait;

    public ApiRequests(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

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

        int statusCode = response.statusCode();
        String responseBody = response.body();
        HttpHeaders headers = response.headers();

        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);
        System.out.println("Response headers: " + headers);
    }


    public JSONObject retrieveVerificationEmail() throws IOException {
        UserDTO dto = new UserDTO();
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        String token = obtainAccessToken(dto.getEmail(), "string");
        dto.setToken(token);
        org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://api.mail.tm/messages");
        httpGet.setHeader("Authorization", "Bearer " + token);

        org.apache.http.HttpResponse response = httpClient.execute(httpGet);

        int responseCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
        System.out.println(responseCode);

        if (responseCode == 200) {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray emailMessages = jsonResponse.getJSONArray("hydra:member");
            System.out.println(emailMessages.length());

            if (emailMessages.length() >= 0) {
                JSONObject message = emailMessages.getJSONObject(0);
                String messageId = message.getString("id");
                System.out.println(messageId);
                String messageEndpointUrl = URL.MAIL_API_BASE_URL + "/messages/" + messageId;

                HttpGet messageHttpGet = new HttpGet(messageEndpointUrl);
                messageHttpGet.setHeader("Authorization", "Bearer " + token);

                org.apache.http.HttpResponse messageResponse = httpClient.execute(messageHttpGet);

                int messageResponseCode = messageResponse.getStatusLine().getStatusCode();
                System.out.println(messageResponseCode);

                if (messageResponseCode == 200) {
                    System.out.println("mtav");
                    String messageResponseBody = EntityUtils.toString(messageResponse.getEntity());
                    System.out.println(messageResponseBody);


                    JSONObject messageJsonObject;
                    try {
                        messageJsonObject = new JSONObject(messageResponseBody);
                        System.out.println(messageJsonObject);

                        String fieldValue = messageJsonObject.getString("text");
                        dto.setRegistrationMail(fieldValue);
                        System.out.println("text: " + fieldValue);

                        return messageJsonObject;

                    } catch (JSONException e) {
                        System.err.println("Failed to parse response as JSON object: " + e.getMessage());
                    }

                } else {
                    throw new IOException("Failed to retrieve verification email. Response code: " + messageResponseCode);
                }
            }
        } else {
            throw new IOException("Failed to retrieve email messages. Response code: " + responseCode);
        }

        return null;
    }
    public JSONObject retrieveForgetPasswordEmail() throws IOException {
        UserDTO dto = new UserDTO();
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String token = obtainAccessToken(dto.getEmail(), "string");
        dto.setToken(token);
        org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://api.mail.tm/messages");
        httpGet.setHeader("Authorization", "Bearer " + token);

        org.apache.http.HttpResponse response = httpClient.execute(httpGet);

        int responseCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
        System.out.println(responseCode);

        if (responseCode == 200) {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray emailMessages = jsonResponse.getJSONArray("hydra:member");
            System.out.println(emailMessages.length());

            if (emailMessages.length() >= 2) {
                JSONObject message = emailMessages.getJSONObject(0);
                String messageId = message.getString("id");
                System.out.println(messageId);
                String messageEndpointUrl = URL.MAIL_API_BASE_URL + "/messages/" + messageId;

                HttpGet messageHttpGet = new HttpGet(messageEndpointUrl);
                messageHttpGet.setHeader("Authorization", "Bearer " + token);

                org.apache.http.HttpResponse messageResponse = httpClient.execute(messageHttpGet);

                int messageResponseCode = messageResponse.getStatusLine().getStatusCode();
                System.out.println(messageResponseCode);

                if (messageResponseCode == 200) {
                    System.out.println("mtav");
                    String messageResponseBody = EntityUtils.toString(messageResponse.getEntity());
                    System.out.println(messageResponseBody);


                    JSONObject messageJsonObject;
                    try {
                        messageJsonObject = new JSONObject(messageResponseBody);
                        System.out.println(messageJsonObject);

                        String fieldValue = messageJsonObject.getString("text");
                        dto.setRegistrationMail(fieldValue);
                        System.out.println("text: " + fieldValue);

                        return messageJsonObject;

                    } catch (JSONException e) {
                        System.err.println("Failed to parse response as JSON object: " + e.getMessage());
                    }

                } else {
                    throw new IOException("Failed to retrieve verification email. Response code: " + messageResponseCode);
                }
            }
        } else {
            throw new IOException("Failed to retrieve email messages. Response code: " + responseCode);
        }

        return null;
    }



    private static String obtainAccessToken(String username,String password) throws IOException {
        String authEndpointUrl = "https://api.mail.tm/token";

        HttpURLConnection connection = createConnection(authEndpointUrl, "POST");
        connection.setRequestProperty("Content-Type", "application/json");

        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("address", username);
        requestBodyJson.put("password", password);
        connection.getOutputStream().write(requestBodyJson.toString().getBytes());

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String responseBody = getResponseBody(connection);
            JSONObject jsonResponse = new JSONObject(responseBody);
            System.out.println("Token:" + jsonResponse.getString("token"));
            return jsonResponse.getString("token");
        } else {
            throw new IOException("Failed to obtain access token. Response code: " + responseCode);
        }
    }


    public String generateRandomEmailForTest() throws IOException {
        try {
            UserDTO dto = new UserDTO();
            String endpointUrl = "https://api.mail.tm/accounts";

            HttpURLConnection connection = createConnection(endpointUrl, "POST");
            connection.setRequestProperty("Content-Type", "application/ld+json");

            String randomEmail = generateRandomEmail();

            String emailWithDomain = randomEmail + "@internetkeno.com";
            String pass = "string";

            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("address", emailWithDomain);
            requestBodyJson.put("password", pass);

            connection.getOutputStream().write(requestBodyJson.toString().getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                String responseBody = getResponseBody(connection);
                JSONObject jsonResponse = new JSONObject(responseBody);

                String generatedEmail = jsonResponse.getString("address");
                dto.setEmail(generatedEmail);
                System.out.println("Generated Email: " + generatedEmail);

                return generatedEmail;
            } else {
                throw new IOException("Failed to generate random email. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateRandomEmail() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public void performRegistration(String email) throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        driver.get(URL.Registration_URL);

        general.enterText(registrationLocators.usernameField, dto.getFullName());
        general.enterText(registrationLocators.emailField, email);
        System.out.println(email);
        general.enterText(registrationLocators.passwordField, dto.getPassword());
        System.out.println(dto.getPassword());

        general.enterText(registrationLocators.confirmPasswordField, dto.getPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        registrationPage.enterDate("2000", "03", "20");
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getGrade());
        general.clickElement(registrationLocators.nextButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.uniDropDown));
        general.selectFromFancyDropdown(registrationLocators.uniDropDown, registrationLocators.uniClass, dto.getUniversity());
        general.selectFromFancyDropdown(registrationLocators.academicDropDown, registrationLocators.academicClass, dto.getAcademics());
        general.clickElement(registrationLocators.fall2024);
        general.clickElement(registrationLocators.early);
        general.clickElement(registrationLocators.yes);
        general.clickElement(registrationLocators.yess);
        general.clickElement(registrationLocators.nextButton2);
        general.enterText(registrationLocators.admTextbox, dto.getAdmText());
        general.clickElement(registrationLocators.privacyPolicy);
        general.clickElement(registrationLocators.submit);
        general.clickElement(registrationLocators.verifyButton);
    }

    public static HttpURLConnection createConnection(String url, String requestMethod) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new java.net.URL(url).openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        return connection;
    }

    private static String getResponseBody(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
    public String extractVerificationLink(String emailContent) {
        Pattern pattern = Pattern.compile("Verify\n\\[(https?://\\S+)\\]");
        Matcher matcher = pattern.matcher(emailContent);

        if (matcher.find()) {
            String verificationLink = matcher.group(1);
            return verificationLink;
        } else {
            return null;
        }
    }
    public String extractForgetPasswordLink(String emailContent) {
        Pattern pattern = Pattern.compile("Reset My Password\n\\[(https?://\\S+)\\]");
        Matcher matcher = pattern.matcher(emailContent);

        if (matcher.find()) {
            String verificationLink = matcher.group(1);
            return verificationLink;
        } else {
            return null;
        }
    }

}
