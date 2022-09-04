package com.supelpawel.springbooksproject.movie.dto;

import com.supelpawel.springbooksproject.movie.model.Movie;
import com.supelpawel.springbooksproject.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
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

    @ManyToOne
    private User user;

    public static MovieDto map(Movie movie) {

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