package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto addNewItem(Integer userId, ItemDto itemDto) {
        User user = userService.getUserById(userId);
        Item item = itemMapper.toItem(itemDto);
        item.setOwner(user);
        return itemMapper.toItemDto(itemRepository.addNewItem(item));
    }

    @Override
    public ItemDto updateItem(Integer userId, Integer itemDtoId, ItemDto itemDto) {
        userService.getUserById(userId);
        getItemByUserId(userId, itemDtoId);
        Item item = itemMapper.toItem(itemDto);
        Item updatedItem = itemRepository.updateItem(item, itemDtoId);
        return itemMapper.toItemDto(updatedItem);
    }

    @Override
    public ItemDto getItemByUserId(Integer userId, Integer itemDtoId) {
        userService.getUserById(userId);
        Item item = itemRepository.getItemByUserId(userId, itemDtoId);
        if (item == null) {
            throw new EntityNotFoundException("Вещи с таким id не существует");
        }
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getItemById(Integer itemDtoId) {
        Item item = itemRepository.getItemById(itemDtoId);
        if (item == null) {
            throw new EntityNotFoundException("Вещи с таким id не существует");
        }
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemDtosByUserId(Integer userId) {
        return itemRepository.getAllItemsByUserId(userId)
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItemDtosByText(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchItemsByText(text.toLowerCase())
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
