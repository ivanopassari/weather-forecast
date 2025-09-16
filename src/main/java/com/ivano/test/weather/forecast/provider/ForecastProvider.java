package com.ivano.test.weather.forecast.provider;

import com.ivano.test.weather.forecast.model.ForecastModel;

import java.security.ProviderException;

/**
 * Created by gladio on 06/10/18.
 */
public interface ForecastProvider {

    public ForecastModel getCityForecasts(String city, String countryCode) throws ProviderException;
}
