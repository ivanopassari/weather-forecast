package com.ivano.test.weather.forecast.provider;

import com.ivano.test.weather.forecast.adapter.ForecastAdapter;
import com.ivano.test.weather.forecast.model.ForecastModel;
import com.ivano.test.weather.forecast.provider.model.Forecast;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.ProviderException;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by gladio on 06/10/18.
 */
@Component
public class ForecastProviderImpl implements ForecastProvider {

    private static final Logger logger = Logger.getLogger(ForecastProviderImpl.class);

    public static final String METRIC = "metric";
    public static final String FORECAST_ENDPOINT = "/forecast";

    @Autowired
    RestTemplate restTemplate;

    @Value("${provider.forecast.baseUrl}")
    String forecastBaseUrl;

    @Value("${provider.forecast.auth.token}")
    String forecastAuthToken;

    @Autowired
    @Qualifier("forecastAdapterImpl")
    ForecastAdapter forecastAdapter;


    public ForecastModel getCityForecasts(String city, String countryCode) throws ProviderException {
        if (Objects.isNull(city) || Objects.isNull(countryCode)) return null;

        logger.info(new StringBuilder().append("Require forecast for city: ").append(city).append(" country code: ").append(countryCode).toString());

        try {
            String url = new StringBuilder().append(forecastBaseUrl).append(FORECAST_ENDPOINT).toString();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("q", new StringBuilder().append(city).append(",").append(countryCode).toString())
                    .queryParam("APPID", forecastAuthToken)
                    .queryParam("units", METRIC);

            HttpEntity<?> entity = new HttpEntity<>(getHttpHeaders());
            String uri = builder.toUriString();
            logger.debug(new StringBuilder().append("Calling provider, uri: ").append(uri).toString());

            ResponseEntity<Forecast> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Forecast.class);
            logger.debug(new StringBuilder().append("Response Status ").append(response.getStatusCode().toString()).toString());
            Forecast body = response.getBody();
            return forecastAdapter.toForecastModel(body);
        } catch (HttpClientErrorException e) {
            ForecastModel forecastModel = new ForecastModel();
            forecastModel.setCity(city);
            forecastModel.setCity(countryCode);
            return forecastModel;
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }


}
