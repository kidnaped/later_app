package ru.practicum.laterapp.note;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class ItemNoteController {
    private final ItemNoteService itemNoteService;

    @GetMapping(params = "url")
    public List<ItemNoteDto> searchByUrl(@RequestHeader("X-Later-User-Id") long userId,
                                         @RequestParam String url) {
        return itemNoteService.searchNotesByUrl(userId, url);
    }

    @GetMapping(params = "tag")
    public List<ItemNoteDto> searchByTags(@RequestHeader("X-Later-User-Id") long userId,
                                          @RequestParam String tag) {
        return itemNoteService.searchNotesByTag(userId, tag);
    }

    @GetMapping
    public List<ItemNoteDto> getAllNotes(@RequestHeader("X-Later-User-Id") long userId,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {
        return itemNoteService.getAllItemsWithNotes(userId, from, size);
    }

    @PostMapping
    public ItemNoteDto add(@RequestHeader("X-Later-User-Id") long userId,
                           @RequestBody ItemNoteDto dto) {
        return itemNoteService.addNewItemNote(userId, dto);
    }
}
