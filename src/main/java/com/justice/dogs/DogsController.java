package com.justice.dogs;

import org.springframework.web.bind.annotation.RestController;

import com.justice.dogs.holder.Dog;
import com.justice.dogs.holder.DogNotFoundException;
import com.justice.dogs.holder.DogsRepo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DogsController {

    private DogsRepo repo;

    public DogsController(DogsRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/home")
    public String homepage(@RequestParam(value = "param", defaultValue = "World!") String param) {
        return String.format("Hello %s", param); // %s is formatted to become the param, if no param is entered, it will instead display "World!"
    }

    @GetMapping("/dogs") // displays all dogs
    public Iterable<Dog> all() {
        return repo.findAll();
    }
    
    @GetMapping("dogs/{id}") // for finding a single dog by id
    public Dog findOne(@PathVariable Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new DogNotFoundException(id));
    }
    
    @GetMapping("/dogs/name/{name}") // without /name in the URI, it leads to a 500 error
    public Dog findFrenchieByName(@PathVariable String name) {
        return repo.findByName(name);
    }

    @PostMapping("/dogs") // allows for a new Dog class (with the name and color parameters) to be added or an already existing one to be updated
    public Dog newDog(@RequestBody Dog newDog) {
        return repo.save(newDog);
    }
       
    @PutMapping("/dogs/{id}")
    public Dog replaceFrenchie(@RequestBody Dog newDog, @PathVariable Long id) {
        // using the map method and a lambda expression, if a frenchie with the id is found, 
        // a newDog variable is created and assigned with a name and color.
        // if the id is not found, the newDog variable gets put into the repository as a new entry.
        return repo.findById(id).map(dog -> {
            dog.setName(newDog.getName());
            dog.setColor(newDog.getColor());
            return repo.save(dog);
        }) .orElseGet(() -> {
            return repo.save(newDog);
        });
    }

    @DeleteMapping("dogs/{id}")
    public void removeFrenchie(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
