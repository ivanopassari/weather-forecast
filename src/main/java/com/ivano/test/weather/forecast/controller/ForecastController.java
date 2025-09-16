package com.ivano.test.weather.forecast.controller;

import com.ivano.test.weather.forecast.model.ForecastResponse;
import com.ivano.test.weather.forecast.service.ForecastService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by gladio on 06/10/18.
 */
@RestController
@Validated
public class ForecastController {

    @Autowired
    private ForecastService forecastService;

    @ApiOperation(value = "View forecast by city", response = ForecastResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 400, message = "Your request is not valid"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public @ResponseBody
    ForecastResponse data(@RequestParam @Size(min = 1, max = 64, message = "city code must be between 1 and 64 chars") String city,
                          @RequestParam @Size(min = 1, max = 2, message = "country code must be between 1 and 2 chars") String countryCode) {

        return forecastService.getCityForecasts(city, countryCode);

    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ForecastResponse handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        ForecastResponse forecastResponse = new ForecastResponse();
        forecastResponse.setErrorDescription(strBuilder.toString());
        forecastResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        return forecastResponse;
    }

}
