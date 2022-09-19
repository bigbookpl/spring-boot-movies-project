package com.supelpawel.springbootmoviesproject.movie.dto;

import com.supelpawel.springbootmoviesproject.movie.model.Movie;
import com.supelpawel.springbootmoviesproject.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String plot;
  private String genre;
  private String director;
  private int year;
  private String poster;
  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> users = new ArrayList<>();

  public static MovieDto from(Movie movie) {
    MovieDto movieDto = new MovieDto();

    movieDto.setTitle(movie.getTitle());
    movieDto.setPlot(movie.getPlot());
    movieDto.setGenre(movie.getGenre());
    movieDto.setDirector(movie.getDirector());
    movieDto.setYear(movie.getYear());
    movieDto.setPoster(movie.getPoster());
    return movieDto;
  }
}