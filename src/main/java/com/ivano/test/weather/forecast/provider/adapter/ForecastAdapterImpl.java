package com.ivano.test.weather.forecast.provider.adapter;

import com.ivano.test.weather.forecast.adapter.ForecastAdapter;
import com.ivano.test.weather.forecast.model.ForecastElement;
import com.ivano.test.weather.forecast.model.ForecastModel;
import com.ivano.test.weather.forecast.provider.model.City;
import com.ivano.test.weather.forecast.provider.model.Element;
import com.ivano.test.weather.forecast.provider.model.Forecast;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gladio on 06/10/18.
 */
@Component
public class ForecastAdapterImpl implements ForecastAdapter<Forecast> {

    @Override
    public ForecastModel toForecastModel(Forecast forecast) {
        if (Objects.isNull(forecast)) return null;
        ForecastModel forecastModel = new ForecastModel();

        Optional<City> city = Optional.ofNullable(forecast.getCity());
        city.ifPresent(e -> forecastModel.setCity(e.getName()));
        city.ifPresent(e -> forecastModel.setCountry(e.getCountry()));

        List<Element> list = forecast.getList();
        if (Optional.ofNullable(list).isPresent()) {
            Map<LocalDateTime, ForecastElement> collect = list.stream().collect(Collectors.toMap(e ->toLocalDateTime(e.getDt()),
                    e -> new ForecastElement(e.getMain() != null ? e.getMain().getPressure() : null, e.getMain() != null ? e.getMain().getTemp() : null)));
            Optional<LocalDateTime> max = collect.keySet().stream().max(LocalDateTime::compareTo);
            Optional<LocalDateTime> min = collect.keySet().stream().min(LocalDateTime::compareTo);
            Optional.ofNullable(min).ifPresent(e -> forecastModel.setFirstForecastDay(e.get()));
            Optional.ofNullable(max).ifPresent(e -> forecastModel.setLastForecastDay(e.get()));

            forecastModel.setForecastElements(collect);
        }


        return forecastModel;
    }

    private static LocalDateTime toLocalDateTime(Integer dt) {

        return Instant.ofEpochMilli(Long.valueOf(dt.toString() + "000")).atZone(ZoneId.of("GMT")).toLocalDateTime();
    }
}
