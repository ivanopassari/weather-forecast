
package com.ivano.test.weather.forecast.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds {

    @JsonProperty("all")
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }


}
