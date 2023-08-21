package se.systementor.dag1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.systementor.dag1.services.ForecastService;

import java.util.Scanner;

@SpringBootApplication
public class Dag1Application implements CommandLineRunner {

	@Autowired
	private  ForecastService forecastService;

	public static void main(String[] args) {

		SpringApplication.run(Dag1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var scan = new Scanner(System.in);

		while(true){
			System.out.println("1. List all");
			System.out.println("2. Create");
			System.out.println("9. Exit");
			System.out.print("Action:");
			int sel = scan.nextInt();
			if(sel == 1){
				listPredictions();
			}
			else if(sel == 2){
				addPrediction();
			}
			else if(sel == 9){
				break;
			}

		}
	}
	private void addPrediction() {
		//Input på dag, hour, temp
		//Anropa servicen - Save

	}
	private void listPredictions() {

	}
}
