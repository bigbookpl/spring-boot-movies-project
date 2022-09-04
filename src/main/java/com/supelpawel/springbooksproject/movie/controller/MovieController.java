package com.supelpawel.springbooksproject.movie.controller;

import com.supelpawel.springbooksproject.movie.dto.MovieDto;
import com.supelpawel.springbooksproject.movie.service.MovieService;
import com.supelpawel.springbooksproject.user.model.CurrentUser;
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
    public String processFindMovieForm(@RequestParam String title, @RequestParam int year, Model model) throws IOException, InterruptedException {

        MovieDto movieDto = movieService.findByTitleAndYearOfRelease(title, year);
        model.addAttribute("movie", movieDto);

        return "movie/searched";
    }

    @GetMapping("/movie/favourite/{title}/{year}")
    String addMovieToFavouriteList(@PathVariable String title, @PathVariable int year,
                                   @AuthenticationPrincipal CurrentUser currentUser, Model model) throws IOException, InterruptedException {

        return movieService.addMovieToFavouriteList(title, year, currentUser, model);

    }

    @GetMapping("/movie/favourite")
    String showFavouriteMovieList(@AuthenticationPrincipal CurrentUser currentUser, Model model) {

        return movieService.showFavouriteMovieList(currentUser, model);
    }

    @GetMapping("/movie/top3")
    String showTop3FavouriteMovies(Model model) throws IOException, InterruptedException {
        return movieService.findTop3FavouriteMovies(model);
    }
}


