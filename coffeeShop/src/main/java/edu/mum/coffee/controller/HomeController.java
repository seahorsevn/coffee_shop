package edu.mum.coffee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.mum.coffee.domain.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
        private final String WEB_SERVICE_URL = "http://localhost:8080";
    
	@GetMapping({"/", "/index", "/home"})
	public String homePage() {
		return "home";
	}
        
        @GetMapping("/createPerson")
	public String addPersonPage() {
		return "addPerson";
	}
        
        @PostMapping("/createPerson")
	public String addPersonPagePost() {
		return "addPerson";
	}
        
        @GetMapping({"/allPeople"})
	public String peoplePage(Model model) throws IOException {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(WEB_SERVICE_URL+"/person/public/all", String.class);
//            System.out.println(result);
            ObjectMapper mapper = new ObjectMapper();
            List<Person> people = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            model.addAttribute("people", people);
            return "listPerson";
	}

	@GetMapping({"/secure"})
	public String securePage() {
		return "secure";
	}
}
