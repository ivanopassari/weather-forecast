package com.ivano.test.weather.forecast.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gladio on 06/10/18.
 */
public class ForecastModel {

    private String city;
    private String country;
    private LocalDateTime lastForecastDay;
    private LocalDateTime firstForecastDay;
    Map<LocalDateTime, ForecastElement> forecastElements = new HashMap<>();

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getLastForecastDay() {
        return lastForecastDay;
    }

    public void setLastForecastDay(LocalDateTime lastForecastDay) {
        this.lastForecastDay = lastForecastDay;
    }

    public LocalDateTime getFirstForecastDay() {
        return firstForecastDay;
    }

    public void setFirstForecastDay(LocalDateTime firstForecastDay) {
        this.firstForecastDay = firstForecastDay;
    }

    public Map<LocalDateTime, ForecastElement> getForecastElements() {
        return forecastElements;
    }

    public void setForecastElements(Map<LocalDateTime, ForecastElement> forecastElements) {
        this.forecastElements = forecastElements;
    }

    public void addForecastElement(LocalDateTime localDateTime, ForecastElement forecastElement) {
        this.forecastElements.put(localDateTime, forecastElement);
    }
}
