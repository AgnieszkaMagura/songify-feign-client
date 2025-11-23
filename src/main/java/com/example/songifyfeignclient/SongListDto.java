package com.example.songifyfeignclient;

import java.util.Map;

public record SongListDto(Map<String, SongResponseDto> songs) {
}
