package com.supelpawel.springbooksproject.movie.repository;

import com.supelpawel.springbooksproject.movie.dto.MovieDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieDto, Long> {
}
