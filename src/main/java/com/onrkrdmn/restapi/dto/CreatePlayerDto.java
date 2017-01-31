package com.onrkrdmn.restapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by onur on 23.01.17.
 * DTO object for Player {@link com.onrkrdmn.domain.Player}
 */
@Getter
@Setter
public class CreatePlayerDto implements BaseDto {
    @NotNull
    @Size(min = 1)
    private String userName;

    @NotNull
    @Size(min = 1)
    private String color;
}
