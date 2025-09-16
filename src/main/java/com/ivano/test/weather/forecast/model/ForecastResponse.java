package com.ivano.test.weather.forecast.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by gladio on 06/10/18.
 */
public class ForecastResponse implements Serializable {

    @ApiModelProperty(notes = "The city name")
    private String city;
    @ApiModelProperty(notes = "The country code")
    private String country;
    @ApiModelProperty(notes = "The status code")
    private String statusCode;
    @ApiModelProperty(notes = "The error description")
    private String errorDescription;
    @ApiModelProperty(notes = "Avg pressure for the next 3 days")
    private Double avgPressure;
    @ApiModelProperty(notes = "Daily avg temp 6am-18pm for the next 3 days")
    private Double dailyAvgTemp;
    @ApiModelProperty(notes = "Nightly avg temp 18pm-6am for the next 3 days")
    private Double nightlyAvgTemp;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAvgPressure() {
        return avgPressure;
    }

    public void setAvgPressure(Double avgPressure) {
        this.avgPressure = avgPressure;
    }

    public Double getDailyAvgTemp() {
        return dailyAvgTemp;
    }

    public void setDailyAvgTemp(Double dailyAvgTemp) {
        this.dailyAvgTemp = dailyAvgTemp;
    }

    public Double getNightlyAvgTemp() {
        return nightlyAvgTemp;
    }

    public void setNightlyAvgTemp(Double nightlyAvgTemp) {
        this.nightlyAvgTemp = nightlyAvgTemp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
