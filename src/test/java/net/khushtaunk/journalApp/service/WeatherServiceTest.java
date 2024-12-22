package net.khushtaunk.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;
    @Test
    void getWeather() {
        weatherService.getWeather("Mysore");
    }
}
