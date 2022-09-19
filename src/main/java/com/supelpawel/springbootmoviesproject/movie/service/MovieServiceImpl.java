package com.supelpawel.springbootmoviesproject.movie.service;

import com.supelpawel.springbootmoviesproject.movie.dto.MovieDto;
import com.supelpawel.springbootmoviesproject.movie.model.Movie;
import com.supelpawel.springbootmoviesproject.movie.repository.MovieRepository;
import com.supelpawel.springbootmoviesproject.user.model.CurrentUser;
import com.supelpawel.springbootmoviesproject.user.model.User;
import com.supelpawel.springbootmoviesproject.user.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Data
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

  public static final int NUMBER_OF_TOP_MOVIES = 3;
  public static final String OMDB_API_KEY = "b1d18668";
  private final MovieRepository movieRepository;
  private final UserService userService;
  private final WebClient client;

  @Override
  public MovieDto findByTitleAndYearOfRelease(String title, int year) {
    RequestBodyUriSpec uriSpec = client.method(HttpMethod.GET);

    uriSpec.uri(uriBuilder -> uriBuilder.path("/")
        .queryParam("apikey", OMDB_API_KEY)
        .queryParam("t", title)
        .queryParam("y", year)
        .build()
    );
    Mono<Movie> movie = uriSpec.exchangeToMono(response -> response.bodyToMono(Movie.class));
    Movie returnMovie = movie.block();

    Optional.ofNullable(returnMovie.getTitle())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    return MovieDto.from(returnMovie);
  }

  @Override
  public String processFindMovieForm(String title, int year, Model model) {
    MovieDto movieDto = findByTitleAndYearOfRelease(title, year);

    model.addAttribute("movie", movieDto);
    return "movie/searched";
  }

  @Override
  @Transactional
  public String addMovieToFavouriteList(String title, int year, CurrentUser currentUser,
      Model model) {
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
}
