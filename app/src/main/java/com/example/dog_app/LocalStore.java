package com.example.dog_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dog_app.models.Dog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalStore {

    public static String DOGS_SHARED_PREFERENCES = "DOGS";
    public static LocalStore instance;
    private final List<Dog> container;

    private Dog dogForDetails;
    private LocalStore(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Dogs",Context.MODE_PRIVATE);
        Set<String> allDogsStringSet =  sp.getStringSet(DOGS_SHARED_PREFERENCES, new HashSet<>());
        Gson g = new Gson();
        List<Dog> container = new ArrayList<>();
        for (String dogString : allDogsStringSet) {
            container.add(g.fromJson(dogString,Dog.class));
        }
        this.container = container;
    }

    public void saveStorage (Context context) {
        SharedPreferences sp = context.getSharedPreferences("Dogs",Context.MODE_PRIVATE);
        Gson g = new Gson();
        Set<String> allDogsStringSet = new HashSet<>();
        for (Dog dog : container) {
            allDogsStringSet.add(g.toJson(dog));
        }
        sp.edit().putStringSet(DOGS_SHARED_PREFERENCES,allDogsStringSet)
                .apply();
    }

    public List<Dog> getDogs() {
        return container;
    }

    public static void createStore(Context context) {
        if(instance==null) {
            instance = new LocalStore(context);
        }
    }

    public void setDogForDetails(Dog dog) {
        this.dogForDetails = dog;
    }

    public Dog getDogForDetails() {
        return dogForDetails;
    }

    public static LocalStore getInstance(Context context) {
        if (instance == null) {
            createStore(context);
        }
        return instance;
    }

    public void removeDogFromStorage(Context context, Dog dog) {
        container.remove(dog);
        saveStorage(context);
    }

    public void addDogToStorage(Context context,Dog dog) {
        container.add(dog);
        saveStorage(context);
        System.out.println(dog);
    }

}
