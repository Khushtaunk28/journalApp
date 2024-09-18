package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.apiResponse.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class weatherService {
    private static final String apiKey="9d25472903051d020d814a7e1a6faaa7";
    private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;

        public WeatherApiResponse getWeather(String city) {
            String finalAPI=API.replace("CITY",city).replace("API_KEY",apiKey);

            ResponseEntity<WeatherApiResponse> response=restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherApiResponse.class);
            WeatherApiResponse body=response.getBody();
            return body;
    }


}
