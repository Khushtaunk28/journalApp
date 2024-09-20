package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.Constants.PlaceHolders;
import net.engineeringdigest.journalApp.apiResponse.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class weatherService {


    @Autowired
    private RedisService redisService;

    @Value("${weather.api.key}")
    private  String apiKey;
    //private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

        public WeatherApiResponse getWeather(String city) {
            WeatherApiResponse weatherApiResponse = redisService.get("weather_of_" + city, WeatherApiResponse.class);
            if(weatherApiResponse!=null){
                return weatherApiResponse;
            }
            else {
                String finalAPI = appCache.APP_CACHE.get("weather_api").replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apiKey);
                ResponseEntity<WeatherApiResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherApiResponse.class);
                WeatherApiResponse body = response.getBody();
                if(body!=null){
                    redisService.set("weather_of_"+city,body,300l);
                }
                return body;
            }
    }


}
