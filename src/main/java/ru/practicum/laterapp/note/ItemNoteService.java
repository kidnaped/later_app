package ru.practicum.laterapp.note;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemNoteService {
    @Transactional
    ItemNoteDto addNewItemNote(long userId, ItemNoteDto itemNoteDto);

    List<ItemNoteDto> searchNotesByUrl(long userId, String url);

    List<ItemNoteDto> searchNotesByTag(long userId, String tag);

    List<ItemNoteDto> getAllItemsWithNotes(long userId, int from, int size);
}
