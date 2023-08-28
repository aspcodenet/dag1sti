package se.systementor.dag1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.systementor.dag1.models.Forecast;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ForecastService {
    private static List<Forecast> forecasts = new ArrayList<>();

    public ForecastService(){
        try {
            forecasts = readFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Forecast> readFromFile() throws IOException {
        if(!Files.exists(Path.of("predictions.json"))) return new ArrayList<Forecast>();
        ObjectMapper objectMapper = getObjectMapper();
        var jsonStr = Files.readString(Path.of("predictions.json"));
        return  new ArrayList(Arrays.asList(objectMapper.readValue(jsonStr, Forecast[].class ) ));
    }


    private void writeAllToFile(List<Forecast> weatherPredictions) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);


        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, weatherPredictions);

        Files.writeString(Path.of("predictions.json"), stringWriter.toString());

    }


    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.registerModule(new JavaTimeModule());
    /*
    <dependency>
        <groupId>com.fasterxml.jackson.datatype</groupId>
        <artifactId>jackson-datatype-jsr310</artifactId>
    </dependency>
    */
        return mapper;
    }



    public List<Forecast> getForecasts(){
        return forecasts;
    }
    public void add(Forecast forecast) throws IOException {
        forecasts.add(forecast);
        writeAllToFile(forecasts);
    }

    public Forecast getByIndex(int i) {
        return forecasts.get(i);
    }

    public void update(Forecast forecast) throws IOException {
        //
        writeAllToFile(forecasts);
    }

    public Optional<Forecast> get(UUID id) {
        return getForecasts().stream().filter(forecast -> forecast.getId().equals(id))
                .findFirst();
    }
}




