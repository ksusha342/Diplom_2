package org.example.user;


import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {
    public static User random() {
        var faker = new Faker(Locale.ENGLISH);
        return new User(
                faker.numerify("myTest#####@yandex.ru"),
                faker.numerify("Qwerty_#####"),
                faker.name().name());
    }
}