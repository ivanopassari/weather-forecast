package com.ivano.test.weather.forecast.service;

import com.ivano.test.weather.forecast.model.ForecastElement;
import com.ivano.test.weather.forecast.model.ForecastModel;
import com.ivano.test.weather.forecast.model.ForecastResponse;
import com.ivano.test.weather.forecast.provider.ForecastProvider;
import com.ivano.test.weather.forecast.provider.ForecastProviderImpl;
import com.ivano.test.weather.forecast.util.ForecastConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.ProviderException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gladio on 06/10/18.
 */
@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    ForecastProvider forecastProvider;

    @Value("${daily.start.time}")
    Integer dailyStartTime;

    @Value("${daily.end.time}")
    Integer dailyEndTime;


    private static final Logger logger = Logger.getLogger(ForecastProviderImpl.class);

    Map<String, ForecastModel> forecastCache = new HashMap<>();


    public ForecastResponse getCityForecasts(String city, String country) {
        logger.info(new StringBuilder().append("getCityForecasts: city: ").append(city).
                append(" country: ").append(country).toString());
        ForecastResponse forecastResponse = new ForecastResponse();
        forecastResponse.setCity(city);
        forecastResponse.setCountry(country);
        ForecastModel forecastModel;
        try {
            String cacheKey = city.toLowerCase() + "_" + country.toLowerCase();

            forecastModel = forecastCache.get(cacheKey);
            if (!Optional.ofNullable(forecastModel).isPresent())
                forecastModel = getCityForecast(city, country);

            if (forecastModel.getForecastElements().isEmpty()) {
                forecastResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                forecastResponse.setErrorDescription(ForecastConstants.CITY_NOT_FOUND_ERROR_DESC);
                return forecastResponse;
            }

            LocalDateTime forecastStartTime = getForecastStartTime(dailyStartTime, dailyEndTime);
            LocalDateTime forecastEndTime = forecastStartTime.plusDays(3);

            if (forecastEndTime.isAfter(forecastModel.getLastForecastDay()))
                forecastModel = getCityForecast(city, country);

            Map<LocalDateTime, ForecastElement> forecastElements = forecastModel.getForecastElements();
            Set<LocalDateTime> timeInterval = forecastElements.keySet().stream().filter(e -> isBeetweenDate(e, forecastStartTime, forecastEndTime)).collect(Collectors.toSet());

            double dailyAvgTemp = timeInterval.stream().filter(e -> isDailyHour(e, dailyStartTime, dailyEndTime)).mapToDouble(e -> forecastElements.get(e).getTemp()).average().getAsDouble();
            double nightlyAvgTemp = timeInterval.stream().filter(e -> !isDailyHour(e, dailyStartTime, dailyEndTime)).mapToDouble(e -> forecastElements.get(e).getTemp()).average().getAsDouble();
            double avgPressure = timeInterval.stream().mapToDouble(e -> forecastElements.get(e).getPressure()).average().getAsDouble();

            forecastResponse.setDailyAvgTemp(dailyAvgTemp);
            forecastResponse.setNightlyAvgTemp(nightlyAvgTemp);
            forecastResponse.setAvgPressure(avgPressure);
            forecastResponse.setStatusCode(HttpStatus.OK.toString());
            return forecastResponse;
        } catch (ProviderException e) {
            logger.debug("City not found: " + city + " country code: " + country);
            forecastResponse.setErrorDescription(ForecastConstants.CITY_NOT_FOUND_ERROR_DESC);
            forecastResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            return forecastResponse;
        } catch (Exception ex) {
            forecastResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return forecastResponse;
        }
    }

    private ForecastModel getCityForecast(String city, String country) throws ProviderException {
        String cacheKey = city.toLowerCase() + "_" + country.toLowerCase();
        ForecastModel forecastModel = forecastProvider.getCityForecasts(city, country);
        if (!forecastModel.getForecastElements().isEmpty()) forecastCache.put(cacheKey, forecastModel);
        return forecastModel;
    }

    private boolean isDailyHour(LocalDateTime time, int dailyHourStart, int dailyHourEnd) {
        LocalDateTime start = getSpecificTimeHourOfTheDay(time, dailyHourStart);
        LocalDateTime end = getSpecificTimeHourOfTheDay(time, dailyHourEnd);
        return isBeetweenDate(time, start, end);
    }

    private static boolean isBeetweenDate(LocalDateTime dateTime, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (dateTime.isAfter(startDateTime) || dateTime.isEqual(startDateTime)) && (dateTime.isBefore(endDateTime) || dateTime.isEqual(endDateTime));
    }

    private LocalDateTime getForecastStartTime(int forecastWindowStartTime, int forecastWindowEndTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = getSpecificTimeHourOfTheDay(now, forecastWindowStartTime);
        LocalDateTime end = getSpecificTimeHourOfTheDay(now, forecastWindowEndTime);
        if (now.isBefore(start)) return start;
        if (now.isBefore(end)) return end;
        return getSpecificTimeHourOfTheDay(end.plusDays(1), forecastWindowStartTime);
    }

    private LocalDateTime getSpecificTimeHourOfTheDay(LocalDateTime day, int hour) {
        LocalDateTime of = LocalDateTime.of(day.toLocalDate(), LocalTime.MIDNIGHT);
        return of.plusHours(hour);
    }


}
