package com.supelpawel.springbootmoviesproject.movie.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

  @JsonProperty("Title")
  private String title;
  @JsonProperty("Year")
  private int year;
  @JsonProperty("Rated")
  private String rated;
  @JsonProperty("Released")
  private String released;
  @JsonProperty("Runtime")
  private String runtime;
  @JsonProperty("Genre")
  private String genre;
  @JsonProperty("Director")
  private String director;
  @JsonProperty("Writer")
  private String writer;
  @JsonProperty("Actors")
  private String actors;
  @JsonProperty("Plot")
  private String plot;
  @JsonProperty("Language")
  private String language;
  @JsonProperty("Country")
  private String country;
  @JsonProperty("Awards")
  private String awards;
  @JsonProperty("Poster")
  private String poster;
  @JsonProperty("Ratings")
  @OneToMany
  private List<Rating> ratings = new ArrayList<>();
  @JsonProperty("Metascore")
  private String metaScore;
  private String imdbRating;
  private String imdbVotes;
  @JsonProperty("imdbID")
  private String imbdId;
  @JsonProperty("Type")
  private String type;
  @JsonProperty("DVD")
  private String dvd;
  @JsonProperty("BoxOffice")
  private String boxOffice;
  @JsonProperty("Production")
  private String production;
  @JsonProperty("Website")
  private String website;
  @JsonProperty("Response")
  private String response;
}
