package com.example.fn;

import java.util.ArrayList;
import java.util.List;

public class HelloFunction {

    public List<Pet> handleRequest() {
        return getAllPet();
    }

    private List<Pet> getAllPet() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet(1, "dog"));
        pets.add(new Pet(2, "cat"));
        pets.add(new Pet(3, "bird"));
        pets.add(new Pet(4, "fish"));
        pets.add(new Pet(5, "snake"));
        return pets;
    }

}