package com.justice.dogs.dogsHolder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// since this class is an interface, methods cannot have bodies
@Repository
public interface DogsRepo extends JpaRepository<Dog, Long> {
    public Dog findByName(String name); // a method to allow a dog to be found by name
    public Dog findByBreed(String breed);
    // public Dog findOne(Long id); 
}
