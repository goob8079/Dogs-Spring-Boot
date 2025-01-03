package com.justice.dogs;

import com.justice.dogs.holder.Dog;
import com.justice.dogs.holder.DogNotFoundException;
import com.justice.dogs.holder.DogsRepo;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DogsController {

    @Autowired
    private DogsRepo repo;

    public DogsController(DogsRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/home") // the homepage
    public String homepage(Model model) {
        return "index";
    }
    
    @GetMapping("/home/dogslist") // takes user to the page where they can add dogs to a database
    public String dogsListPage(Model model) {
        model.addAttribute("dogs", repo.findAll());
        return "dogs-list";
    }

    @GetMapping("/home/dogtypes") // takes user to a page about different dogs
    public String dogTypesPage(Model model) {
        return "dog-types";
    }

    @GetMapping("/home/pibbletypes") // a page only to display types of pibbles
    public String pibbleTypesPage(Model model) {
        return "pibble-types";
    }
    
    @GetMapping("/dogs/find/{id}") // for finding a single dog by id
    public Dog findOne(@PathVariable("id") long id) {
        return repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
    }
    
    @GetMapping("/dogs/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Dog dog = repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
        
        model.addAttribute("dog", dog);
        return "update-dog";
    }

    @GetMapping("/home/newdog")
    public String signUpForm(Dog dog) {
        return "add-dog";
    }
   
    @GetMapping("/dogs/add") 
    public String showNewDog(Model model) {
        model.addAttribute("dog", new Dog());
        return "add-dog";
    }

    @PostMapping("/dogs/add") 
    public String newDog(@Valid Dog dog, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-dog";
        }
        
        repo.save(dog);
        return "redirect:/home";
    }
       
    @PostMapping("/dogs/update/{id}") // @Valid is used to validate the dog variable
    public String updateDog(@PathVariable("id") long id, @Valid Dog dog, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/home";
        }
        repo.save(dog);
        return "redirect:/home";
    }

    @GetMapping("/dogs/delete/{id}") // can't use DeleteMapping here or else a 405 error occurs
    public String removeDog(@PathVariable("id") long id, Model model) {
        Dog dog = repo.findById(id)
        .orElseThrow(() -> new DogNotFoundException(id));
        repo.delete(dog);
        return "redirect:/home";
    }    
}
