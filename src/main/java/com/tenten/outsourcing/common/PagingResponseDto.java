package com.tenten.outsourcing.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PagingResponseDto {

    private final int currentPage;

    private final int totalPages;

    private final List<?> contents;
}
