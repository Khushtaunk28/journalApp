package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.apiResponse.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class weatherService {

    @Value("${weather.api.key}")
    private  String apiKey;
    //private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

        public WeatherApiResponse getWeather(String city) {
            String finalAPI=appCache.APP_CACHE.get("weather_api").replace("<city>",city).replace("<apikey>",apiKey);

            ResponseEntity<WeatherApiResponse> response=restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherApiResponse.class);
            WeatherApiResponse body=response.getBody();
            return body;
    }


}
