package com.example.coverranking.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;

public enum Genre {
    BALLAD("Ballad"),
    KPOP("Kpop"),
    RAP("Rap"),
    INDIE("Indie"),
    TEUROTEU("Teuroteu"),
    ROCK("Rock"),
    RANDB("RandB"),
    CHILDREN("Children"),
    DANCE("Dance");
    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Genre forValue(String value) {
        for (Genre genre : Genre.values()) {
            if (genre.displayName.equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Unknown genre: " + value);
    }
}
