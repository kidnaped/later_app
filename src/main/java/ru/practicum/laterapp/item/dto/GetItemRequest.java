package ru.practicum.laterapp.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetItemRequest {
    private long userId;
    private State state;
    private ContentType contentType;
    private Sort sort;
    private Integer limit;
    private List<String> tags;

    public static GetItemRequest of(long userId,
                                    String state,
                                    String contentType,
                                    String sort,
                                    int limit,
                                    List<String> tags) {
        return GetItemRequest.builder()
                .userId(userId)
                .limit(limit)
                .state(State.valueOf(state.toUpperCase()))
                .contentType(ContentType.valueOf(contentType.toUpperCase()))
                .sort(Sort.valueOf(sort.toUpperCase()))
                .tags(tags != null ? tags : new ArrayList<>())
                .build();
    }

    public boolean hasTags() {
        return tags != null && tags.isEmpty();
    }

    public enum State {UNREAD, READ, ALL }
    public enum ContentType { ARTICLE, VIDEO, IMAGE, ALL }
    public enum Sort { NEWEST, OLDEST, TITLE, SITE }
}
