package com.example.songifyfeignclient;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableFeignClients
@Log4j2
public class SongifyFeignClientApplication {

    @Autowired
    SongsClient songsClient;

    public static void main(String[] args) {
        SpringApplication.run(SongifyFeignClientApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
        try {
            log.info("==== CREATED ====");
            CreateSongResponseDto songAdded = songsClient.createSong(new CreateSongRequestDto("my best song", "DMC"));
            log.info("Created: {} - {}", songAdded.song().name(), songAdded.song().artist());

            log.info("==== SONG LIST ====");
            SongListDto songs = songsClient.getSongs(5);
            songs.songs().forEach((id, song) ->
                    log.info("{}. {} - {}", id, song.name(), song.artist()));

            log.info("==== GET BY ID ====");
            SongDetailsDto songsById = songsClient.getSongsById(3L);
            log.info("Created: {} - {}",
                    songsById.song().name()
                    , songsById.song().artist());

            log.info("==== PUT ====");
            UpdateSongResponseDto songPutResponse = songsClient.putSong(4L, new UpdateSongRequestDto("In the sky", "Train"));
            log.info("Updated: {} - {}", songPutResponse.song(), songPutResponse.artist());

            log.info("==== GET BY ID ====");
            SongDetailsDto songsById1 = songsClient.getSongsById(4L);
            log.info("Created: {} - {}",
                    songsById1.song().name()
                    , songsById1.song().artist());

            log.info("==== PATCHED ====");
            UpdateSongParcialRequestDto patch = new UpdateSongParcialRequestDto("moja pioseneczka", null);
            UpdateSongParcialResponseDto response = songsClient.patchSong(3L, patch);
            log.info("Patched: {} - {}", response.updatedSong().name(), response.updatedSong().artist());

            log.info("==== SONG LIST ====");
            SongListDto listSongs = songsClient.getSongs(4);
            listSongs.songs().forEach((id, song) ->
                    log.info("{}. {} - {}", id, song.name(), song.artist()));

            log.info("==== DELETED BY QUERY PARAMETER ====");
            DeleteSongResponseDto deleteSongResponseDto = songsClient.deleteByIdUsingQueryParam(3L);
            log.info("Delete response: {}", deleteSongResponseDto.message());

            log.info("==== SONG LIST ====");
            SongListDto listSongs1 = songsClient.getSongs(4);
            listSongs1.songs().forEach((id, song) ->
                    log.info("{}. {} - {}", id, song.name(), song.artist()));

            log.info("==== DELETED BY PATH VARIABLE ====");
            DeleteSongResponseDto songDeleteResponse = songsClient.deleteById(4L);
            log.info("Delete response: {}", songDeleteResponse.message());

            log.info("==== SONG LIST ====");
            SongListDto listSongs2 = songsClient.getSongs(4);
            listSongs2.songs().forEach((id, song) ->
                    log.info("{}. {} - {}", id, song.name(), song.artist()));
        } catch (
                FeignException.FeignClientException feignException) {
            log.error("client exception: " + feignException.status());
        } catch (FeignException.FeignServerException feignException) {
            log.error("server exception: " + feignException.status());
        } catch (
                RetryableException retryableException) {
            log.error("retryable exception" + retryableException.getMessage());
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            log.error(feignException.status());
        }

    }
}
