package ru.practicum.LaterApp.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.LaterApp.item.dto.ItemDto;
import ru.practicum.LaterApp.item.model.Item;
import ru.practicum.LaterApp.user.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item fromDto(ItemDto dto, User user) {
        Item item = new Item();
        if (dto.getUserId() != null) {
            item.setUser(user);
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
                .userId(item.getUser().getId())
                .url(item.getUrl())
                .tags(new ArrayList<>(item.getTags()))
                .build();
    }

    public static List<ItemDto> toDto(Iterable<Item> items) {
        List<ItemDto> dtos = new ArrayList<>();
        items.forEach(item -> dtos.add(toDto(item)));
        return dtos;
    }
}
