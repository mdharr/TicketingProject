package com.mdharr.inventoryservice.playground;

public class Main {
    public static void main(String[] args) {
        Person person = new Person.Builder("Michael", "Harrington")
                .age(36)
                .build();
        System.out.println(person);
    }
}
