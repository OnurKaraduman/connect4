package com.onrkrdmn.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by onur on 22.01.17.
 * indicates the points of board
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Point {
    private int x;
    private int y;
}
