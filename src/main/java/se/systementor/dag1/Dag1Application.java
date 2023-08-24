package se.systementor.dag1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.systementor.dag1.models.BlogPost;
import se.systementor.dag1.models.Forecast;
import se.systementor.dag1.services.ForecastService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class Dag1Application implements CommandLineRunner {

	@Autowired
	private  ForecastService forecastService;

	public static void main(String[] args) {

		int i = 12;
		String namnet = "Stefan";
		float pi = 3.1415927f;
		System.out.printf("Jag är %d år jag kallas %s så ni vet %.3f", i, namnet, pi);


		SpringApplication.run(Dag1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var objectMapper = new ObjectMapper();


		BlogPost []blogPosts = objectMapper.readValue(new URL("https://jsonplaceholder.typicode.com/posts"),
				BlogPost[].class);


		BlogPost blogPost = objectMapper.readValue(new URL("https://jsonplaceholder.typicode.com/posts/1"),
				BlogPost.class);



		var forecast = new Forecast();
		forecast.setId(UUID.randomUUID());
		forecast.setTemperature(12f);
		forecast.setDate(20230101);
		forecast.setHour(12);

		String json = objectMapper.writeValueAsString(forecast);
		System.out.println(json);


		Forecast forecast2 = objectMapper.readValue(json,Forecast.class);




		var scan = new Scanner(System.in);

		while(true){
			System.out.println("1. List all");
			System.out.println("2. Create");
			System.out.println("3. Update");
			System.out.println("9. Exit");
			System.out.print("Action:");
			int sel = scan.nextInt();
			if(sel == 1){
				listPredictions();
			}
			else if(sel == 3){
				updatePrediction(scan);
			}
			else if(sel == 2){


				addPrediction(scan);
			}
			else if(sel == 9){
				break;
			}

		}
	}

	private void updatePrediction(Scanner scan) throws IOException {
		listPredictions();
		System.out.printf("Ange vilken du vill uppdatera:");
		int num = scan.nextInt() ;
		var forecast = forecastService.getByIndex(num-1);
		System.out.printf("%d %d CURRENT: %f %n",
				forecast.getDate(),
				forecast.getHour(),
				forecast.getTemperature()
		);
		System.out.printf("Ange ny temp:");
		float temp = scan.nextFloat() ;
		forecast.setTemperature(temp);
		forecastService.update(forecast);
	}

	private void addPrediction(Scanner scan) throws IOException {
		//Input på dag, hour, temp
		//Anropa servicen - Save
		System.out.println("*** CREATE PREDICTION ***");
		System.out.printf("Ange vilken dag:");
		int dag = scan.nextInt() ;
		System.out.print("Hour:");
		int hour =  scan.nextInt() ;
		System.out.print("Temperature:");
		float temp =  scan.nextFloat() ;

		var forecast = new Forecast();
		forecast.setId(UUID.randomUUID());
		forecast.setDate(dag);
		forecast.setHour(hour);
		forecast.setTemperature(temp);

		forecastService.add(forecast);
	}
	private void listPredictions() {
		int num = 1;
		for(var forecast : forecastService.getForecasts()){
			System.out.printf("%d %d %d %f %n",
					num,
					forecast.getDate(),
					forecast.getHour(),
					forecast.getTemperature()
					);
			num++;
		}
	}
}
