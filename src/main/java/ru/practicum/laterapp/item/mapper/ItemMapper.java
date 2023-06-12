package ru.practicum.laterapp.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.laterapp.item.UrlMetaDataRetriever;
import ru.practicum.laterapp.item.dto.ItemDto;
import ru.practicum.laterapp.item.model.Item;
import ru.practicum.laterapp.user.model.User;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    private static final DateTimeFormatter dtFormatter = DateTimeFormatter
            .ofPattern("yyyy.MM.dd hh:mm:ss")
            .withZone(ZoneOffset.UTC);

    public static Item toItem(UrlMetaDataRetriever.UrlMetadata result, User user, Set<String> tags) {
        Item item = new Item();
        item.setUser(user);
        item.setUrl(result.getNormalUrl());
        item.setResolvedUrl(result.getResolvedUrl());
        item.setMimeType(result.getMimeType());
        item.setTitle(result.getTitle());
        item.setHasImage(result.isHasImage());
        item.setHasVideo(result.isHasVideo());
        item.setDateResolved(result.getDateResolved());
        item.setTags(tags);
        return item;
    }

    public static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .normalUrl(item.getUrl())
                .resolvedUrl(item.getResolvedUrl())
                .hasImage(item.isHasImage())
                .hasVideo(item.isHasVideo())
                .mimeType(item.getMimeType())
                .unread(item.isUnread())
                .dateResolved(dtFormatter.format(item.getDateResolved()))
                .tags(new HashSet<>(item.getTags()))
                .build();
    }

    public static List<ItemDto> toDto(Iterable<Item> items) {
        List<ItemDto> dtos = new ArrayList<>();
        items.forEach(item -> dtos.add(toDto(item)));
        return dtos;
    }
}
