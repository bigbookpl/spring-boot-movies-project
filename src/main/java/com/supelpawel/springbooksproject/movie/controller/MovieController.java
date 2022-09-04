package com.supelpawel.springbooksproject.movie.controller;

import com.supelpawel.springbooksproject.movie.data.MovieDto;
import com.supelpawel.springbooksproject.movie.service.MovieService;
import com.supelpawel.springbooksproject.user.data.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movie/search")
    public String findMovieForm() {

//        MovieDto movieDto = new MovieDto();
//
//        model.addAttribute("movieDto", movieDto);

        return "movie/search";
    }

    @PostMapping("/movie/search")
    @ResponseBody
    public MovieDto processFindMovieForm(@RequestParam String title, @RequestParam int yearOfRelease) throws IOException, InterruptedException {

        return movieService.findByTitleAndYearOfRelease(title, yearOfRelease);
    }

    @PostMapping("/movie/favourite")
    String showFavouriteMovies(@AuthenticationPrincipal CurrentUser currentUser) {

        return "movie/favourite";
    }
}
