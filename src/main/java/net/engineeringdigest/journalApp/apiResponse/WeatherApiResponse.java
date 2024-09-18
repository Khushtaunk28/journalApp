package net.engineeringdigest.journalApp.apiResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class WeatherApiResponse {
    private  Current current;

    @Getter
    @Setter
    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;

        private int temperature;

        @JsonProperty("weather_icons")
        private ArrayList<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDescriptions;

        private int humidity;

        private int feelslike;

        private int visibility;
    }
}





