package com.javabeer.domain.beer;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class BeerId {
    private final String value;
}
