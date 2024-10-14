package com.justice.dogs;

import org.springframework.web.bind.annotation.RestController;

import com.justice.dogs.holder.Dog;
import com.justice.dogs.holder.DogNotFoundException;
import com.justice.dogs.holder.DogsRepo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

    @GetMapping("/home") // displays all dogs; need to use Iterable because it DogsRepo extends CrudRepository. If it extended JpaRepository instead, List could've been used
    public String homepage(Model model) {
        model.addAttribute("viewalldogs", repo.findAll()); 
        return "index";
    }
    
    @GetMapping("/dogs/add")
    public String addNewDog(Model model) {
        Dog dog = new Dog();
        model.addAttribute("dog", dog);
        return "newdog";
    }
    
    @GetMapping("/dogs/{id}") // for finding a single dog by id
    public Dog findOne(@PathVariable long id) {
        return repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
    }
    
    @GetMapping("/dogs/name/{name}") // without /name in the URI, it leads to a 500 error
    public Dog findDogByName(@PathVariable String name) {
        return repo.findByName(name);
    }

    @GetMapping("/dogs/breed/{breed}")
    public Dog findDogbyBreed(String breed) {
        return repo.findByBreed(breed);
    }

    @GetMapping("/showupdated/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        Dog dog = repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
        
        model.addAttribute("dog", dog);
        return "update-dog";
    }

    @PostMapping("/dogs/add") // allows for a new Dog class (with the breed, name, and color parameters) to be added or an already existing one to be updated
    public String newDog(@ModelAttribute("dog") Dog newDog) {
        repo.save(newDog);
        return "redirect:/home";
    }
       
    @PostMapping("/dogs/update/{id}")
    public String updateDog(@PathVariable long id, @Valid Dog dog, Model model) {
        repo.save(dog);
        return "redirect:/home";
    }

    @DeleteMapping("/dogs/delete/{id}")
    public String removeDog(@PathVariable long id, Model model) {
        Dog dog = repo.findById(id)
        .orElseThrow(() -> new DogNotFoundException(id));
        repo.deleteById(id);
        return "redirect:/home";
    }
}
