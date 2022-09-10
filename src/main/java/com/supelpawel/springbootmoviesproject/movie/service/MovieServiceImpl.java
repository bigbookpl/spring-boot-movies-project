package com.supelpawel.springbootmoviesproject.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supelpawel.springbootmoviesproject.movie.dto.MovieDto;
import com.supelpawel.springbootmoviesproject.movie.model.Movie;
import com.supelpawel.springbootmoviesproject.movie.repository.MovieRepository;
import com.supelpawel.springbootmoviesproject.user.model.CurrentUser;
import com.supelpawel.springbootmoviesproject.user.model.User;
import com.supelpawel.springbootmoviesproject.user.service.UserService;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

  public static final int NUMBER_OF_TOP_MOVIES = 3;
  public static final String OMDB_API_KEY = "b1d18668";
  public static final String SPACE = " ";
  public static final String URL_ENCODED_SPACE = "%20";
  private final MovieRepository movieRepository;
  private final UserService userService;

  @Override
  public MovieDto findByTitleAndYearOfRelease(String title, int year)
      throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
        .uri(URI.create(createUniqueApiUrl(title, year))).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ObjectMapper mapper = new ObjectMapper();
    Movie movie = mapper.readValue(response.body(), Movie.class);

    return MovieDto.from(movie);
  }

  @Override
  public String processFindMovieForm(String title, int year, Model model)
      throws IOException, InterruptedException {
    MovieDto movieDto = findByTitleAndYearOfRelease(title, year);

    model.addAttribute("movie", movieDto);
    return "movie/searched";
  }

  @Override
  @Transactional
  public String addMovieToFavouriteList(String title, int year, CurrentUser currentUser,
      Model model) throws IOException, InterruptedException {
    User user = currentUser.getUser();
    MovieDto movieDto;

    if (currentUser.getUser().getFavouriteMovies().stream()
        .anyMatch(m -> title.equals(m.getTitle()))) {
      return "movie/warning";
    }

    if (movieRepository.findMovieByTitle(title) == null) {
      movieDto = findByTitleAndYearOfRelease(title, year);
    } else {
      movieDto = findMovieByTitle(title);
    }
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
    List<MovieDto> topMovies = movieRepository.findTopMovies();

    if (topMovies.size() > NUMBER_OF_TOP_MOVIES) {
      topMovies = topMovies.subList(0, NUMBER_OF_TOP_MOVIES);
    }
    model.addAttribute("topMovies", topMovies);
    return "movie/top3";
  }

  @Override
  @Transactional
  public String deleteMovieFromFavouriteList(CurrentUser currentUser, int id) {
    User user = currentUser.getUser();
    List<MovieDto> movies = user.getFavouriteMovies();
    Optional<MovieDto> optMovieToDelete = movies.stream().filter(m -> m.getId() == id).findFirst();

    if (optMovieToDelete.isPresent()) {
      MovieDto movieToDelete = optMovieToDelete.get();
      user.getFavouriteMovies().remove(movieToDelete);
      userService.update(user);
      movieToDelete.getUsers().remove(user);

      if (movieToDelete.getUsers().isEmpty()) {
        movieRepository.delete(movieToDelete);
      } else {
        movieRepository.save(movieToDelete);
      }
    }
    return "redirect:/movie/favourite";
  }

  public MovieDto findMovieByTitle(String title) {
    return movieRepository.findMovieByTitle(title);
  }

  private String createUniqueApiUrl(String title, int year) {
    String uniqueUrl = String.format("https://www.omdbapi.com/?apikey=%s&t=%s&year=%d",
        OMDB_API_KEY, title, year);
    return uniqueUrl.replaceAll(SPACE, URL_ENCODED_SPACE);
  }
}
