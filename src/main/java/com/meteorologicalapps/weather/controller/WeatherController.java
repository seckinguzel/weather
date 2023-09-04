package com.meteorologicalapps.weather.controller;

import com.meteorologicalapps.weather.controller.validation.CityNameConstraint;
import com.meteorologicalapps.weather.dto.WeatherDto;
import com.meteorologicalapps.weather.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@Validated //This runs primitive values for instance String, Integer but if we use DTO for to request then we should @Valid annotation in the method.
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @RateLimiter(name = "basic") //Its name is set "basic" on the application.yml
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") @CityNameConstraint @NotBlank String city) { //If we use DTO in here we should use @Valid but this is just Sting so we have to use @Validated annotation top of the class
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }
}
