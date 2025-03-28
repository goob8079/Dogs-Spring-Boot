package com.justice.dogs.dogsHolder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // makes an object ready for storage in a database
@Table(name = "dog")
public class Dog {

    // a random value is generated for the dogs' ID
    private @Id @GeneratedValue long id;
    
    @Column(name = "breed", unique = false, nullable = false)
    private String breed;
    
    @Column(name = "name", unique = false, nullable = false)
    private String name;
    
    @Column(name = "color", unique = false, nullable = false)
    private String color;

    public Dog() {}

    public Dog(String breed, String name, String color) {
        this.breed = breed;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getBreed() {
        return breed;
    }
    
    public String getColor() {
        return color;
    }
    
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Breed: " + breed + ", Name: " + name + ", Color: " + color;
    }
}
