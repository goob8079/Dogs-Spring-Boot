package com.justice.dogs.holder;

import org.springframework.data.repository.CrudRepository;


// for accessing data
// allows for CRUD
public interface DogsRepo extends CrudRepository<Dog, Long> {
    public Dog findByName(String name); // a method to allow a dog to be found by name
    public Dog findByBreed(String breed); 
}
