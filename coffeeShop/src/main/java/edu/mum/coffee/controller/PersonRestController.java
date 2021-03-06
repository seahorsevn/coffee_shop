/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.coffee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import java.util.List;
import edu.mum.coffee.service.PersonService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Son Vu
 */
@RestController
public class PersonRestController {
    
    @Autowired
    private PersonService personService;
    
    @GetMapping(RestURIConstant.PERSON_ALL)
    public ResponseEntity getPersonAll(){
        List<Person> list = personService.getAll();
        
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping({RestURIConstant.PERSON_CREATE,RestURIConstant.PERSON_RIGISTER})
    public ResponseEntity createPerson(@ModelAttribute Person p){
        personService.savePerson(p);
        return new ResponseEntity(p,HttpStatus.OK);
    }
    
    @PostMapping(RestURIConstant.PERSON_UPDATE)
    public ResponseEntity updatePerson(@RequestParam("id") long id,@ModelAttribute Person p){
        
        Person updated  = personService.findById(id);
        updated.setAddress(p.getAddress());
        updated.setFirstName(p.getFirstName());
        updated.setLastName(p.getLastName());
        updated.setEmail(p.getEmail());
        updated.setPhone(p.getPhone());
        personService.savePerson(updated);
        
        return new ResponseEntity(p,HttpStatus.OK);
    }
    
    @GetMapping(RestURIConstant.PERSON_FIND)
    public ResponseEntity findPerson(@PathVariable long id){
        Person p = personService.findById(id);
        return new ResponseEntity(p,HttpStatus.OK);
    }
    
//    @PostMapping(value=RestURIConstant.PERSON_FIND_BY_EMAIL,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(RestURIConstant.PERSON_FIND_BY_EMAIL)
//    public ResponseEntity findPersonByEmail(){
    public ResponseEntity findPersonByEmail(HttpEntity entity){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = mapper.convertValue(entity.getBody(), Map.class);
        List<Person> p = personService.findByEmail(map.get("email"));
        if(p.size()>0){
            return new ResponseEntity(p.get(0),HttpStatus.OK);
        }
        return new ResponseEntity(map.get("email"),HttpStatus.OK);
    }
}
