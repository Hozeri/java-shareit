package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.error.ErrorHandler;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validationgroups.Create;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ErrorHandler errorHandler;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addNewItem(@RequestHeader(USER_ID_HEADER) Integer userId,
                              @RequestBody @Validated(Create.class) ItemDto itemDto,
                              BindingResult bindingResult) {
        errorHandler.throwValidationException(bindingResult);
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemDtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto updateItem(@RequestHeader(USER_ID_HEADER) Integer userId,
                              @PathVariable Integer itemDtoId,
                              @RequestBody ItemDto itemDto) {
        return itemService.updateItem(userId, itemDtoId, itemDto);
    }

    @GetMapping("/{itemDtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getItemById(@PathVariable Integer itemDtoId) {
        return itemService.getItemById(itemDtoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getItemsByUserId(@RequestHeader(USER_ID_HEADER) Integer userId) {
        return itemService.getAllItemDtosByUserId(userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.searchItemDtosByText(text);
    }
}
