package com.ravishka.megacitycab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MegacitycabApplication {

	public static void main(String[] args) {
		SpringApplication.run(MegacitycabApplication.class, args);
	}

	@RestController
	public class WelcomeController {
		@GetMapping("/")
		public Map<String, Object> start() {
			Map<String, Object> response = new HashMap<>();
				
			return response;
		}
	}
}
