package com.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;

    private String itemNum;

    private  String name;

    private Integer price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime registertime;

    private LocalDateTime updatetime;
}
