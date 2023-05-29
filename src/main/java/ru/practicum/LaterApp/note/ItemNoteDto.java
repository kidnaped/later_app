package ru.practicum.LaterApp.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemNoteDto {
    private Long id;
    private Long itemId;
    private String text;
    private String dateOfNote;
    private String itemUrl;
}
