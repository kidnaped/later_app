package ru.practicum.LaterApp.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCountByUser {
    private Long userId;
    private Long count;
}
