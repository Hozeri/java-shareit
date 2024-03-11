package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addNewItem(Integer userId, ItemDto itemDto);

    ItemDto updateItem(Integer userId, Integer itemDtoId, ItemDto itemDto);

    ItemDto getItemByUserId(Integer userId, Integer itemDtoId);

    ItemDto getItemById(Integer itemDtoId);

    List<ItemDto> getAllItemDtosByUserId(Integer userId);

    List<ItemDto> searchItemDtosByText(String text);
}
