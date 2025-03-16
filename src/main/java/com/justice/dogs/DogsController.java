package com.justice.dogs;

import com.justice.dogs.exceptions.DogNotFoundException;
import com.justice.dogs.holder.Dog;
import com.justice.dogs.holder.DogsRepo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// This is not a RESTful API, rather it requests which serves HTML views and displays the corresponding pages
// This application uses Controller, which allows for dyamic pages with data processing.

// Model is used to supply attributes used for rendering views. 
// It holds application data, that is used by Thymeleaf to render responses(views).

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
    public Dog findOne(@PathVariable long id) {
        return repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
    }
    
    @GetMapping("/dogs/update/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        Dog dog = repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
        
        model.addAttribute("dog", dog);
        return "update-dog";
    }
    
    @PostMapping("/dogs/update/{id}") // @Valid is used to validate the dog variable
    public String updateDog(@PathVariable("id") long id, @Valid Dog dog, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/home/dogslist";
        }
        repo.save(dog);
        return "redirect:/home/dogslist";
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
        
        // after the user enters the information about the dog
        // it gets added to the repo variable's MySQL table
        // then redirects the user back to the dogslist page
        repo.save(dog);
        return "redirect:/home/dogslist";
    }   

    @GetMapping("/dogs/delete/{id}") // can't use DeleteMapping here or else a 405 error occurs
    public String removeDog(@PathVariable("id") long id, Model model) {
        Dog dog = repo.findById(id)
        .orElseThrow(() -> new DogNotFoundException(id));
        repo.delete(dog);
        return "redirect:/home";
    }
}
