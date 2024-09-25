package com.example.everytime.universityName.api.dto;

import com.example.everytime.universityName.domain.UniversityName;

public record UniversityNameDto(
        String name
) {
    public static UniversityNameDto from(UniversityName universityName) {
        return new UniversityNameDto(
                universityName.getName()
        );
    }
}
