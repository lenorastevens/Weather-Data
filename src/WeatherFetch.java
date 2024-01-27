import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;


public class WeatherFetch {
    private String zipcode;

    public WeatherFetch(String zipcode) {
        this.zipcode = zipcode;
    }

    public Map<String, String> fetchWeatherData() {
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        Map<String, String> weatherData = new HashMap<>();

        // Get API key
        String key = "";
        try {
            File myObj = new File("apikey.txt");
            Scanner myReader = new Scanner(myObj);
            key = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("API file not found");
        }
        // Define the URL for the API endpoint
        
        String template = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/next7days?unitGroup=us&key=%s&contentType=json";
        String apiUrl = String.format(template, zipcode, key);
        URI uri = URI.create(apiUrl);

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json") // Set the appropriate headers
                .GET() // Use GET method, or use POST() for POST requests
                .build();

        try {
            // Send the request and retrieve the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonData = new JSONObject(response.body()); 

            // JSONObject jsonObject = new JSONObject(jsonData);

            // Extract and display the desired information
            JSONObject currentConditions = jsonData.getJSONObject("currentConditions");
    
            double temperature = currentConditions.getDouble("temp");
            String conditions = currentConditions.getString("conditions");
            double feelsLike = currentConditions.getDouble("feelslike");
            double humidity = currentConditions.getDouble("humidity");
    
            weatherData.put("Temperature", String.format("%.2f", temperature));
            weatherData.put("Conditions", conditions);
            weatherData.put("Feels Like", String.format("%.2f", feelsLike));
            weatherData.put("Humidity", String.format("%.2f%%", humidity));


            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
            weatherData.put("Error", "Error fetching weather data");
            return weatherData;
        }
    }
}   

