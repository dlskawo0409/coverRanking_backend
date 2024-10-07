package com.example.coverranking.member.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter
public class GenreListConverter implements AttributeConverter<List<Genre>, Integer> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Integer convertToDatabaseColumn(List<Genre> attribute) {
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
    public List<Genre> convertToEntityAttribute(Integer sum) {
        ArrayList<Genre> list = new ArrayList<>();
        for (int i = 0; i < Genre.values().length; i++) {
            if ((sum & (1 << i)) != 0) {
                list.add(Genre.values()[i]);
            }
        }
        return list;
    }


}
