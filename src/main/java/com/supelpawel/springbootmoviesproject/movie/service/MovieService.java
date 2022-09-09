package com.supelpawel.springbootmoviesproject.movie.service;

import com.supelpawel.springbootmoviesproject.movie.dto.MovieDto;
import com.supelpawel.springbootmoviesproject.user.model.CurrentUser;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Repository
public interface MovieService {

  MovieDto findByTitleAndYearOfRelease(String title, int year)
      throws IOException, InterruptedException;

  String processFindMovieForm(String title, int year, Model model)
      throws IOException, InterruptedException;

  String addMovieToFavouriteList(String title, int year, CurrentUser currentUser, Model model)
      throws IOException, InterruptedException;

  String showFavouriteMovieList(CurrentUser currentUser, Model model);

  List<MovieDto> findByUserId(long id);

  String findTop3FavouriteMovies(Model model) throws IOException, InterruptedException;

  String deleteMovieFromFavouriteList(CurrentUser currentUser, int id);
}
