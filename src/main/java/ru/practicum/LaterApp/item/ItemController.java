package ru.practicum.LaterApp.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.LaterApp.item.dto.ItemDto;
import ru.practicum.LaterApp.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Later-User-Id") long userId,
                             @RequestParam(required = false) Set<String> tags) {
        return itemService.getItems(userId, tags);
    }

    @GetMapping(params = "lastName")
    public List<ItemDto> get(@RequestParam String lastName) {
        return itemService.getUserItems(lastName);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") Long userId,
                    @RequestBody @Valid ItemDto dto) {
        return itemService.addNewItem(userId, dto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }
}
