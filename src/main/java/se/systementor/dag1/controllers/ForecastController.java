package se.systementor.dag1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.systementor.dag1.models.Forecast;
import se.systementor.dag1.services.ForecastService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// 1, CLienbt anriooar /api/forecasts GET
//2. Spring kollar vilken funktion hanterar denna /api/forecasts
//3. Spring anropar den funktionen
// 3.5 VÅR KOD KÖRS
//4. Spring tar det som funktionen returnerar och gör till JSON
//5. Spring skickar tillbaka JSON till client

@RestController
public class ForecastController {
    @Autowired
    private ForecastService forecastService;
    @GetMapping("/api/forecasts")
    public ResponseEntity<List<Forecast>> getAll(){
        return new ResponseEntity<>(forecastService.getForecasts(), HttpStatus.OK);
    }

    @GetMapping("/api/forecasts/{id}")
    public ResponseEntity<Forecast> Get(@PathVariable UUID id){
        Optional<Forecast> forecast = forecastService.get(id);
        if(forecast.isPresent()) return ResponseEntity.ok(forecast.get());
        return  ResponseEntity.notFound().build();
    }

//    @PutMapping("/api/products/{id}")
//    public ResponseEntity<Product> Update(@PathVariable UUID id, @RequestBody Product product){
//        boolean status = productService.update(product);
//        if(status == true)
//            return ResponseEntity.ok(product);
//        else
//            return ResponseEntity.badRequest().build();
//    }


}
