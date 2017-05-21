package edu.mum.coffee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.mum.coffee.domain.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
        private final String WEB_SERVICE_URL = "http://localhost:8080";
    
	@GetMapping({"/", "/index", "/home"})
	public String homePage() {
            return "home";
	}
        
        @RequestMapping("/createPerson")
	public String addPersonPage() {
            return "addPerson";
	}
        
        @GetMapping("/allPeople")
	public String peoplePage(Model model) throws IOException {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(WEB_SERVICE_URL+RestURIConstant.PERSON_ALL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            List<Person> people = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            model.addAttribute("people", people);
            return "listPerson";
	}
        
        @GetMapping("/detailPerson/{id}")
	public String updatePersonPage(@PathVariable long id,Model model) throws IOException {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(WEB_SERVICE_URL+"/person/public/"+id, String.class);
            ObjectMapper mapper = new ObjectMapper();
            Person p = mapper.readValue(result, Person.class);
            model.addAttribute("person", p);
            return "editPerson";
	}
        
        
        //This method received post request and forward it to REST
        //after receiving the result from REST, it will redirect to a web page
        @PostMapping("/updatePerson")
	public String updatePerson(HttpEntity<String> requestEntity){
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(WEB_SERVICE_URL+RestURIConstant.PERSON_UPDATE, requestEntity,Person.class);
            return "redirect:/allPeople";
	}

	@GetMapping({"/secure"})
	public String securePage() {
		return "secure";
	}
}
