package com.supelpawel.springbootmoviesproject.movie.controller;

import com.supelpawel.springbootmoviesproject.movie.service.MovieService;
import com.supelpawel.springbootmoviesproject.user.model.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping("/movie/search")
  public String searchMovieForm() {
    return "movie/search";
  }

  @PostMapping("/movie/search")
  public String processFindMovieForm(@RequestParam String title, @RequestParam int year,
      Model model) throws IOException, InterruptedException {
    return movieService.processFindMovieForm(title, year, model);
  }

  @GetMapping("/movie/favourite/{title}/{year}")
  String addMovieToFavouriteList(@PathVariable String title, @PathVariable int year,
      @AuthenticationPrincipal CurrentUser currentUser,
      Model model) throws IOException, InterruptedException {
    return movieService.addMovieToFavouriteList(title, year, currentUser, model);
  }

  @GetMapping("/movie/favourite")
  String showFavouriteMovieList(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
    return movieService.showFavouriteMovieList(currentUser, model);
  }

  @GetMapping("/movie/delete/{id}")
  String deleteMovie(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable int id) {
    return movieService.deleteMovieFromFavouriteList(currentUser, id);
  }

  @GetMapping("/movie/top3")
  String showTop3FavouriteMovies(Model model) throws IOException, InterruptedException {
    return movieService.findTop3FavouriteMovies(model);
  }
}

