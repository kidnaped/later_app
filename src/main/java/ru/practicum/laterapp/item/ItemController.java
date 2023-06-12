package ru.practicum.laterapp.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.laterapp.item.dto.GetItemRequest;
import ru.practicum.laterapp.item.dto.ItemCreationDto;
import ru.practicum.laterapp.item.dto.ItemDto;
import ru.practicum.laterapp.item.dto.ModifyItemRequest;
import ru.practicum.laterapp.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Later-User-Id") long userId,
                             @RequestParam(defaultValue = "unread") String state,
                             @RequestParam(defaultValue = "all") String contentType,
                             @RequestParam(defaultValue = "newest") String sort,
                             @RequestParam(defaultValue = "10") int limit,
                             @RequestParam(required = false) List<String> tags) {
        return itemService.getItems(GetItemRequest.of(userId, state, contentType, sort, limit, tags));
    }

    @GetMapping(params = "lastName")
    public List<ItemDto> get(@RequestParam String lastName) {
        return itemService.getUserItems(lastName);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") Long userId,
                       @RequestBody ItemCreationDto dto) {
        return itemService.addNewItem(userId, dto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @PatchMapping
    public ItemDto modifyItem(@RequestHeader("X-Later-User-Id") long userId,
                              @RequestBody ModifyItemRequest request) {
        return itemService.changeItem(userId, request);
    }
}
