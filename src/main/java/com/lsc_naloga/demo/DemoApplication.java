package com.lsc_naloga.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.net.URL;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@GetMapping("/potiDoDatoteke")
	public String[] potiDoDatoteke(@RequestParam(defaultValue = "/") String datoteka) {
		

		System.out.println("Iscem vse poti do " + datoteka + "...");

		JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader("tree_structure.json"));
		} catch (Exception e) {

			String message = "Failed to read JSON file!";

			System.out.println(message);
			e.printStackTrace();
			return new String[0];
		} 

		JSONObject root = (JSONObject) jsonArray.get(0);
		
		ArrayList<String> vsePoti = new ArrayList<String>();

		preisci(root, datoteka, "", vsePoti);
		
		System.out.println("Iskanje datoteke koncano.");

		return vsePoti.toArray(new String[0]);
	}

	static void preisci(JSONObject datoteka, String iskanoIme, String pot, List<String> vsePoti) {

		// nasli iskano datoteko
		if((datoteka.get("type").equals("file") || datoteka.get("type").equals("link")) && datoteka.get("name").equals(iskanoIme)) {
			
			String najdenaPot = pot + iskanoIme;
			System.out.println("datoteka najdena na: " + (pot + iskanoIme));
			
			vsePoti.add(najdenaPot);
			return;
		} 
		// datoteka, ampak ne iskana
		else if(datoteka.get("type").equals("file")) {
			// mrtvi konec
			return;
		}
		// direktorij
		else {
			// iskanje nadaljujemo v vsebini direktorija
			JSONArray vsebina = (JSONArray) datoteka.get("contents");
			
			for(Object o : vsebina) {
				JSONObject datoteka_ = (JSONObject) o;
	
				// nekje je prislo do napak pri branju direktorija
				if(datoteka_.get("error") != null) {
					break;
				}

				preisci(datoteka_, iskanoIme, pot + datoteka.get("name") + "/", vsePoti);
			}	

			return;
		}

	}

}