package com.supelpawel.springbooksproject.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supelpawel.springbooksproject.movie.dto.MovieDto;
import com.supelpawel.springbooksproject.movie.model.Movie;
import com.supelpawel.springbooksproject.movie.repository.MovieRepository;
import com.supelpawel.springbooksproject.user.model.CurrentUser;
import com.supelpawel.springbooksproject.user.model.User;
import com.supelpawel.springbooksproject.user.service.UserService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return MovieDto.map(movie);
    }

    @Override
    public String addMovieToFavouriteList(String title, int year, CurrentUser currentUser, Model model) throws IOException, InterruptedException {

        User user = currentUser.getUser();

        MovieDto movieDto = findByTitleAndYearOfRelease(title, year);
        movieDto.setUser(user);
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
    public String findTop3FavouriteMovies(Model model) throws IOException, InterruptedException {

        List<String> allMovies = getAllFavouriteMovies();

        List<String> top3FavouriteMoviesTitles = findMostFrequentFavouriteMovies(allMovies);

        assert top3FavouriteMoviesTitles != null;
        MovieDto firstMovie = findByTitleAndYearOfRelease(top3FavouriteMoviesTitles.get(1), findYearByTitle(top3FavouriteMoviesTitles.get(1)));
        MovieDto secondMovie = findByTitleAndYearOfRelease(top3FavouriteMoviesTitles.get(2), findYearByTitle(top3FavouriteMoviesTitles.get(2)));
        MovieDto thirdMovie = findByTitleAndYearOfRelease(top3FavouriteMoviesTitles.get(3), findYearByTitle(top3FavouriteMoviesTitles.get(3)));

        List<MovieDto> top3FavouriteMovies = new ArrayList<>();
        top3FavouriteMovies.add(firstMovie);
        top3FavouriteMovies.add(secondMovie);
        top3FavouriteMovies.add(thirdMovie);

        model.addAttribute("top3FavouriteMovies", top3FavouriteMovies);

        return "movie/top3";
    }

    private String createUniqueApiUrl(String title, int year) {
        return "https://www.omdbapi.com/?apikey=b1d18668&t=" + title + "&year=" + year;
    }

    private List<String> getAllFavouriteMovies() {

        List<User> users = userService.findAll();
        List<String> titlesOfAllFavouriteMovies = new ArrayList<>();

        for (User user : users) {

            user.getFavouriteMovies().stream()
                    .map(MovieDto::getTitle)
                    .forEach(titlesOfAllFavouriteMovies::add);
        }

        return titlesOfAllFavouriteMovies;
    }

    private List<String> findMostFrequentFavouriteMovies(List<String> titleList) {

        Map<String, Integer> counts = new HashMap<>();
        List<String> top3FavouriteMovies = new ArrayList<>();

        int highestFrequency = 0;

        for (String s : titleList) {

            int currFrequency = counts.getOrDefault(titleList, 0) + 1;

            counts.put(s, currFrequency);

            highestFrequency = Math.max(currFrequency, highestFrequency);
        }

        for (String s : counts.keySet()) {

            if (counts.get(s) == highestFrequency) {

                if (top3FavouriteMovies.size() <= 2) {

                    top3FavouriteMovies.add(s);

                } else {
                    return top3FavouriteMovies;
                }
            }
        }
        return null;
    }

    @Override
    public int findYearByTitle(String title) {

        return movieRepository.findAll().stream()
                .filter(m -> m.getTitle().equals(title))
                .map(MovieDto::getYear)
                .findFirst()
                .get();
    }
}
