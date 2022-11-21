package com.lsc_naloga.demo;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.lsc_naloga.demo.DemoApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testStruktura1() {

		// preberemo datoteko testna_struktura_1.json
		JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(
			        "testna_struktura_1.json"));
		} catch (Exception e) {

			String message = "Failed to read JSON file!";

			System.out.println(message);
			e.printStackTrace();
		}

		JSONObject root = (JSONObject) jsonArray.get(0);
		
		ArrayList<String> vsePoti = new ArrayList<String>();

		
		DemoApplication.preisci(root, "a.txt", "", vsePoti);

		String[] pravilno = {
			"/a/a.txt",
			"/a/b/a.txt",
			"/a/c/f/a.txt"
		};

		for(String pot : vsePoti) {
			System.out.println(pot);
		}

		assertTrue(vsePoti.size() == pravilno.length);
		assertTrue(vsePoti.contains(pravilno[2]));
		
	}

	@Test
	void testStructure2() {
		// preberemo datoteko testna_struktura_1.json
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(
					"testna_struktura_2.json"));
		} catch (Exception e) {

			String message = "Failed to read JSON file!";

			System.out.println(message);
			e.printStackTrace();
		}

		JSONObject root = (JSONObject) jsonArray.get(0);

		ArrayList<String> vsePoti = new ArrayList<String>();

		DemoApplication.preisci(root, "a", "", vsePoti);

		String[] pravilno = {
			"a"
		};

		assertTrue(vsePoti.get(0).equals(pravilno[0]));
		assertTrue(vsePoti.size() == 1);
	}

	@Test
	void testTreeStructure() {
		JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(
			        "tree_structure.json"));
		} catch (Exception e) {

			String message = "Failed to read JSON file!";

			System.out.println(message);
			e.printStackTrace();
		} 

		JSONObject root = (JSONObject) jsonArray.get(0);
		
		ArrayList<String> vsePoti = new ArrayList<String>();

		String iskanaDatoteka = "README";
		DemoApplication.preisci(root, iskanaDatoteka, "", vsePoti);

		String enPravilen = "/etc/X11/Xreset.d/README";

		assertTrue(vsePoti.contains(enPravilen));
		assertTrue(vsePoti.size() == 9);
	}
}
