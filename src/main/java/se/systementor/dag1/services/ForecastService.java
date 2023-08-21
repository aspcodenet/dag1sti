package se.systementor.dag1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.systementor.dag1.models.Forecast;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastService {
    private static List<Forecast> forecasts = new ArrayList<>();


}

