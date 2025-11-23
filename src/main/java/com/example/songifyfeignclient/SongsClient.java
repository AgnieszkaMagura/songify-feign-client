package com.example.songifyfeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "songify-client", url = "localhost:8080")
public interface SongsClient {

    //GET /songs?limit=x
    @GetMapping("/songs")
    SongListDto getSongs(@RequestParam("limit") int limit);

    // GET /songs/{id}
    @GetMapping("/songs/{id}")
    SongDetailsDto getSongsById(@PathVariable("id") Long id);

    // POST /songs (method with @PostMapping)
    @PostMapping("/songs")
    CreateSongResponseDto createSong(@RequestBody CreateSongRequestDto request);

    // POST /songs (method with @RequestMapping(method = RequestMethod.PATCH ...)
    @RequestMapping(method = RequestMethod.PATCH, value = "/songs/{id}")
    UpdateSongParcialResponseDto patchSongRM(@PathVariable Long id, @RequestBody UpdateSongParcialRequestDto request
    );

    // PATCH songs/{id}
    @PatchMapping("/songs/{id}")
    UpdateSongParcialResponseDto patchSong(@PathVariable Long id, @RequestBody UpdateSongParcialRequestDto request);

    // PUT /songs/{id}
    @PutMapping("/songs/{id}")
    UpdateSongResponseDto putSong(@PathVariable Long id, @RequestBody UpdateSongRequestDto request);

    // DELETE /songs?id=x
    @DeleteMapping("/songs")
    DeleteSongResponseDto deleteByIdUsingQueryParam(@RequestParam("id") Long id);

    // DELETE /songs/{id}
    @DeleteMapping("/songs/{id}")
    DeleteSongResponseDto deleteById(@PathVariable Long id);


}


