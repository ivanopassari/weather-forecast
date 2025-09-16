##Weather Forecast Project
Spring Boot Application which gets the weather forecast of a specific city.
The Application expose API /data to retrieve the averages of the following 3 Days.
Api Doc must be retrieved through the Swagger Documentation exposed at the url /swagger-ui.html#/

The weather forecasts are calculated from 6:00 to 18:00 for daylight hours, from 18:00 to 6:00 for night hours.
For more accurate data the calculation of the average temperature for night / day hours starts from the next time window.
Example a call made at 16:00 will be calculated from 18:00 up to the next 3 days.

The weather data are requested at the web service https://openweathermap.org/, parameters and API KEY
must be changed on the application.properties file.

## Minimal Requirements

For running the application you must have jdk 8 installed and set the java environment variable in the classpath.

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

##Running Instruction
  - Open terminal and go to the root of the project
  - Type java -jar weather-forecast-1.0.0-SNAPSHOT.jar
  - Documentation url at http://localhost:8080/swagger-ui.html#/
  - Api Example GET http://localhost:8080/data?city=milan&countryCode=it

