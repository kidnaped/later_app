package ru.practicum.laterapp.item.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ItemCreationDto {
    private String url;
    private Set<String> tags = new HashSet<>();
}
