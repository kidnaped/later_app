package ru.practicum.LaterApp.note;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.LaterApp.item.model.Item;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemNoteMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss");
    public static ItemNoteDto toDto(ItemNote itemNote) {
        return ItemNoteDto.builder()
                .id(itemNote.getId())
                .itemId(itemNote.getItem().getId())
                .text(itemNote.getText())
                .dateOfNote(itemNote.getDateOfNote().format(formatter))
                .itemUrl(itemNote.getItem().getUrl())
                .build();
    }

    public static List<ItemNoteDto> toDto(Iterable<ItemNote> itemNotes) {
        List<ItemNoteDto> dtos = new ArrayList<>();
        itemNotes.forEach(itemNote -> dtos.add(toDto(itemNote)));
        return dtos;
    }

    public static ItemNote fromDto(ItemNoteDto itemNoteDto, Item item) {
        ItemNote itemNote = new ItemNote();
        itemNote.setItem(item);
        itemNote.setText(itemNoteDto.getText());
        return itemNote;
    }
}
