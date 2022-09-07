package com.supelpawel.springbootmoviesproject.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supelpawel.springbootmoviesproject.movie.dto.MovieDto;
import com.supelpawel.springbootmoviesproject.movie.model.Movie;
import com.supelpawel.springbootmoviesproject.movie.repository.MovieRepository;
import com.supelpawel.springbootmoviesproject.user.model.CurrentUser;
import com.supelpawel.springbootmoviesproject.user.model.User;
import com.supelpawel.springbootmoviesproject.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserService userService;

    @Override
    public MovieDto findByTitleAndYearOfRelease(String title, int year) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(createUniqueApiUrl(title, year)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        Movie movie = mapper.readValue(response.body(), Movie.class);

        return MovieDto.from(movie);
    }

    @Override
    public String addMovieToFavouriteList(String title, int year, CurrentUser currentUser, Model model) throws IOException, InterruptedException {

        User user = currentUser.getUser();

        if (currentUser.getUser().getFavouriteMovies().stream().anyMatch(m -> title.equals(m.getTitle()))) {
            return "movie/warning";
        }

        MovieDto movieDto = findByTitleAndYearOfRelease(title, year);
        movieDto.getUsers().add(user);
        movieRepository.save(movieDto);

        user.getFavouriteMovies().add(movieDto);
        userService.update(user);

        List<MovieDto> movies = user.getFavouriteMovies();

        model.addAttribute("movies", movies);

        return "movie/favourite";
    }

    @Override
    public String showFavouriteMovieList(CurrentUser currentUser, Model model) {

        User user = currentUser.getUser();

        List<MovieDto> movies = user.getFavouriteMovies();

        model.addAttribute("movies", movies);

        return "movie/favourite";
    }

    @Override
    public List<MovieDto> findByUserId(long id) {
        return findByUserId(id);
    }

    @Override
    public String findTop3FavouriteMovies(Model model) {

        List<MovieDto> topMovies = movieRepository.findTopMovies().subList(0, 3);
        model.addAttribute("topMovies", topMovies);

        return "movie/top3";
    }

    private String createUniqueApiUrl(String title, int year) {
        String uniqueUrl = "https://www.omdbapi.com/?apikey=b1d18668&t=" + title + "&year=" + year;
        return uniqueUrl.replaceAll(" ", "%20");
    }
}
