package ru.practicum.LaterApp.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.LaterApp.item.dto.ItemDto;
import ru.practicum.LaterApp.item.model.Item;

import java.util.ArrayList;
import java.util.HashSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item fromDto(ItemDto dto) {
        Item item = new Item();
        if (dto.getUserId() != null) {
            item.setUserId(dto.getUserId());
        }
        if (dto.getUrl() != null) {
            item.setUrl(dto.getUrl());
        }
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            item.setTags(new HashSet<>(dto.getTags()));
        }
        return item;
    }

    public static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .url(item.getUrl())
                .tags(new ArrayList<>(item.getTags()))
                .build();
    }
}
