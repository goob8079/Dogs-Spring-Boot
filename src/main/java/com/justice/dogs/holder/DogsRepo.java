package com.justice.dogs.holder;

import org.springframework.data.repository.CrudRepository;


// for accessing data
// allows for CRUD
public interface DogsRepo extends CrudRepository<Dog, Long> {
    Dog findByName(String name);
    Dog findByBreed(String breed);
}
