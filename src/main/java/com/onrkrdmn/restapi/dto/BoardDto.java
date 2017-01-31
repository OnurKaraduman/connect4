package com.onrkrdmn.restapi.dto;

import com.onrkrdmn.constant.PlayerColor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by onur on 24.01.17.
 * DTO object for Board {@link com.onrkrdmn.domain.Board}
 */
@Getter
@Setter
public class BoardDto implements BaseDto {

    private Map<PointDto, PlayerColor> points;
}
