package ru.practicum.LaterApp.item.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private Long userId;
    private String url;
    private List<String> tags = new ArrayList<>();
}
