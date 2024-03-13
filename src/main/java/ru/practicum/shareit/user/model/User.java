package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.validationgroups.Create;
import ru.practicum.shareit.validationgroups.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    @NotBlank(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    private String email;
}
