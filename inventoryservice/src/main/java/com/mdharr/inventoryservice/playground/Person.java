package com.mdharr.inventoryservice.playground;

public class Person {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    public static class Builder {
        private final String firstName;
        private final String lastName;

        private int age = 0;

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", age=" + age +
                '}';
    }
}
