package com.example.dog_app;

import com.example.dog_app.models.Dog;

public interface DogItemClickListener {

    void clickedShowMore(Dog dog);
    void clickedDelete(Dog dog);
}
