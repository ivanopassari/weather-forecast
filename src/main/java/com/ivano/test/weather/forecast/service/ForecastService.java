package com.ivano.test.weather.forecast.service;

import com.ivano.test.weather.forecast.model.ForecastResponse;

/**
 * Created by gladio on 06/10/18.
 */
public interface ForecastService {
    ForecastResponse getCityForecasts(String city, String country);
}
