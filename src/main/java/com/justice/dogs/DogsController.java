package com.justice.dogs;

import org.springframework.web.bind.annotation.RestController;

import com.justice.dogs.holder.Dog;
import com.justice.dogs.holder.DogNotFoundException;
import com.justice.dogs.holder.DogsRepo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class DogsController {

    @Autowired
    private DogsRepo repo;

    public DogsController(DogsRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/home") 
    public String homepage(Model model) {
        model.addAttribute("viewalldogs", repo.findAll()); 
        return "index";
    }
    
    // @GetMapping("/dogs/add")
    // public String addNewDog(Model model) {
    //     Dog dog = new Dog();
    //     model.addAttribute("dog", dog);
    //     repo.save(dog);
    //     return "newdog";
    // }
    
    @GetMapping("/dogs/{id}") // for finding a single dog by id
    public Dog findOne(@PathVariable("id") long id) {
        return repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
    }
    
    @GetMapping("/dogs/name/{name}") 
    public Dog findDogByName(@PathVariable("id") String name) {
        return repo.findByName(name);
    }

    @GetMapping("/dogs/breed/{breed}")
    public Dog findDogbyBreed(String breed) {
        return repo.findByBreed(breed);
    }

    @GetMapping("/showupdated/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Dog dog = repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
        
        model.addAttribute("dog", dog);
        return "update-dog";
    }

    @PostMapping("/dogs/add") 
    public String newDog(@Valid @ModelAttribute("dog") Dog dog, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-dog";
        }
        
        model.addAttribute("dog", dog);
        repo.save(dog);
        return "redirect:/home";
    }
       
    @PostMapping("/dogs/update/{id}") // @Valid is used to validate the dog variable
    public String updateDog(@PathVariable("id") long id, @Valid Dog dog, Model model) {
        repo.save(dog);
        return "redirect:/home";
    }

    @DeleteMapping("/dogs/delete/{id}")
    public String removeDog(@PathVariable("id") long id, Model model) {
        Dog dog = repo.findById(id)
        .orElseThrow(() -> new DogNotFoundException(id));
        repo.delete(dog);;
        return "redirect:/home";
    }
}
