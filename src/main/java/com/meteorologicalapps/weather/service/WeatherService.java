package com.meteorologicalapps.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteorologicalapps.weather.dto.WeatherDto;
import com.meteorologicalapps.weather.dto.WeatherResponse;
import com.meteorologicalapps.weather.model.WeatherEntity;
import com.meteorologicalapps.weather.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {
    private static final String API_URL = "http://api.weatherstack.com/current?access_key=434bc3b6cb52e4e631e4a99cd3c657f8&query=";
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate; //Bunu inject edilebilir hale getirmek icin bir konfigurasyon yapmamız gerek config package altinda.
    private final ObjectMapper objectMapper = new ObjectMapper(); //getWeatherFromWeatherStack metodumuzda json verimizi string'e çevirmek icin kullanacagiz.

    public WeatherService(WeatherRepository weatherRepository,
                          RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCityName(String city) {
        Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city); //Optional donmemin sebebi ilgili sehir olmayadabilir null'la ugrasmamak icin bu sekilde kullandim

        if (!weatherEntityOptional.isPresent()) {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
        if(weatherEntityOptional.get().getUpdatedTime().isBefore(LocalDateTime.now().minusSeconds(30))) {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
        return WeatherDto.convert(weatherEntityOptional.get());
    }

    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_URL + city, String.class);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity = new WeatherEntity(city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localtime(), dateTimeFormatter)
        );

        return weatherRepository.save(weatherEntity);
    }
}
