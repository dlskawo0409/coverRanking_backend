package com.example.coverranking.member.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class GenreListConverter implements AttributeConverter<List<Genre>, int> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public int convertToDatabaseColumn(List<Genre> attribute) {
        try {
            int sum = 0;
            for(Genre genre : attribute){
                sum |= (1<<genre.ordinal());
            }
            return sum;
        } catch (Exception e) {
            throw new RuntimeException("JSON writing error", e);
        }
    }

    @Override
    public List<Genre> convertToEntityAttribute(int i) {
        return List.of();
    }

    @Override
    public List<Genre> convertToEntityAttribute(String dbData) {
        try {

            return objectMapper.readValue(dbData, new TypeReference<List<Genre>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON reading error", e);
        }
    }
}
