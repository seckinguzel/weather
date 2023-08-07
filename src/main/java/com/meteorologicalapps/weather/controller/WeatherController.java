package com.meteorologicalapps.weather.controller;

import com.meteorologicalapps.weather.dto.WeatherDto;
import com.meteorologicalapps.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") String city) {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }
}
