package com.justice.dogs.holder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

// for accessing data
// allows for CRUD
// since this class is an interface, methods without bodies can be created
@Repository
@Transactional
public interface DogsRepo extends CrudRepository<Dog, Long> {
    public Dog findByName(String name); // a method to allow a dog to be found by name
    public Dog findByBreed(String breed); 
}
