package com.supelpawel.springbooksproject.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supelpawel.springbooksproject.movie.data.MovieDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Data
@NoArgsConstructor
@Slf4j
public class MovieService {

    public MovieDto findByTitleAndYearOfRelease(String title, int year) throws IOException, InterruptedException {

//        if (title == null || title.trim().length() == 0 || year == 0) {
//            return "movie/search";
//        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(createUniqueApiUrl(title, year)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

//        MovieDto movieDto = mapper.readValue(response.body(), new TypeReference<>() {
//        });

        log.info(response.body());

        MovieDto movieDto = mapper.readValue(response.body(), MovieDto.class);

        return movieDto;

//        Model model = null;

//        model.addAttribute("movie", movieDto);
//
//        return "movie/searched";
    }

    private String createUniqueApiUrl(String title, int year) {
        return "https://www.omdbapi.com/?apikey=b1d18668&t=" + title + "&year=" + year;
    }
}
