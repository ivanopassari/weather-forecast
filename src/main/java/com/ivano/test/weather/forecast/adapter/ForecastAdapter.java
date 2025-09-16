package com.ivano.test.weather.forecast.adapter;

import com.ivano.test.weather.forecast.model.ForecastModel;

/**
 * Created by gladio on 06/10/18.
 */
public interface ForecastAdapter<T> {

    public ForecastModel toForecastModel(T t);
}
