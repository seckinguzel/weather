package com.meteorologicalapps.weather.repository;

import com.meteorologicalapps.weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, String> {
    //"Select * from entity where requestedCityName order by updatedTime desc limit 1" sorgusunun jpa karşıligi asagidaki Optional sorgudur. Bunu yapmamin sebebi ornegin istanbul hava durumu icin 100 kayit atilmis olabilir ben en son guncel kaydi bulmam lazim.
    Optional<WeatherEntity> findFirstByRequestedCityNameOrderByUpdatedTimeDesc(String city);
    //List<WeatherEntity> findAllByRequestedCityNameOrderByUpdatedTimeDesc(String city); bu sekilde yapsaydim eger ornegin 1000 data'yi sıralayip sonuncusunu bulacakti ama buna gerek yok durduk yere neden sisteme yuk bindireyim
}
