package com.onrkrdmn.restapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by onur on 30.01.17.
 * Creat game parameter model
 */
@Getter
@Setter
public class CreateGameDto {
    @NotNull
    @Size(min = 1)
    private String userName;

    @NotNull
    @Size(min = 1)
    private String color;
}
