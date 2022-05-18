package com.javabeer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Beer {
    private BeerId id;
    private String name;
    private String producer;
    private BeerCategory category;
}
