# Songify Feign Client

This project demonstrates how to integrate a Java Spring Boot application with an external REST API using **Spring Cloud OpenFeign**.  
The client communicates with the backend API from:  
👉 https://github.com/kalqa/songify

The application sends requests to the Songify API, processes responses using custom DTOs, and logs the results when the application starts.

---

## 🚀 Technologies Used

- Java 17  
- Spring Boot 3  
- Spring Cloud OpenFeign  
- Maven  
- REST API Integration  

---

## 📌 Project Description

The goal of this project is to practice building a REST client using **Spring Cloud OpenFeign**.  
Instead of exposing its own API, the application connects to the Songify service running on:
http://localhost:8080

All communication is defined in the `SongsClient` interface:

```java
@FeignClient(value = "songify-client", url = "http://localhost:8080")
public interface SongsClient { ... }
After application startup, a set of requests is automatically executed to demonstrate:

retrieving song lists

getting a song by ID

creating a new song

partial update (PATCH)

full update (PUT)

deleting songs

logging all server responses

📚 Available Client Operations

1. GET /songs?limit={limit}

Fetches a list of songs with a limit.

Method: SongListDto getSongs(int limit)

Returns a map of songs (id → song properties)

2. GET /songs/{id}

Fetches details of a single song.

Method: SongDetailsDto getSongsById(Long id)

Returns song name + artist

3. POST /songs

Creates a new song.

Request: SongRequestDto(songName, artist)

Returns: SongDetailsDto with created song

4. PATCH /songs/{id}

Partially updates a song — only provided fields are changed.

Request: SongPatchRequestDto(songName, artist)

Any field may be null → value stays unchanged

Returns: SongPatchResponseDto(updatedSong)

5. PUT /songs/{id}

Fully updates a song — all fields required.

Request: SongPutRequestDto(song, artist)

Returns: SongPutResponseDto(song)

6. DELETE /songs?id={id}

Deletes a song using a query parameter.

7. DELETE /songs/{id}

Deletes a song using the path variable.


public record SongResponseDto(String name, String artist) { }

public record SongDetailsDto(SongResponseDto song) { }

public record SongListDto(Map<String, SongResponseDto> songs) { }

public record SongRequestDto(String songName, String artist) { }

public record SongPatchRequestDto(String songName, String artist) { }

public record SongPatchResponseDto(SongResponseDto updatedSong) { }

public record SongPutRequestDto(String song, String artist) { }

public record SongPutResponseDto(SongResponseDto song) { }

public record SongDeleteResponseDto(String message) { }

▶️ How to Run
1. Start the Songify API

Clone and run the server:
git clone https://github.com/kalqa/songify
cd songify
mvn spring-boot:run

The backend should now be available at:
http://localhost:8080
Swagger UI:
http://localhost:8080/swagger-ui

2. Clone this Feign client
git clone https://github.com/AgnieszkaMagura/songify-feign-client
cd songify-feign-client

3. Run the application
mvn spring-boot:run
4. Check the console output

The application automatically sends:

GET

POST

PATCH

PUT

DELETE

requests and prints results using log.info(...).

📎 Purpose of the Project

This project was created as a homework assignment to practice:

REST client design

DTO mapping

Feign usage

API integration

Clean code naming and client design patterns

It also serves as a portfolio-friendly example of Spring Boot → REST API integration.



