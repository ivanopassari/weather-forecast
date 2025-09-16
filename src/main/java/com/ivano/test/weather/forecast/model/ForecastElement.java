package com.ivano.test.weather.forecast.model;

import java.time.LocalDateTime;

/**
 * Created by gladio on 06/10/18.
 */
public class ForecastElement {
    private Double pressure;
    private Double temp;

    public ForecastElement() {
    }

    public ForecastElement(Double pressure, Double temp) {
        this.pressure = pressure;
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
