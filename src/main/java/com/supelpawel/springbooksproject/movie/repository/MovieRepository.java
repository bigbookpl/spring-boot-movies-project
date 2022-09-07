package com.supelpawel.springbooksproject.movie.repository;

import com.supelpawel.springbooksproject.movie.dto.MovieDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieDto, Long> {

    @Query(value = "SELECT md FROM MovieDto md ORDER BY md.users.size DESC")
    List<MovieDto> findTopMovies();
}
