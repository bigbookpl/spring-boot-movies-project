package com.supelpawel.springbooksproject.movie.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.supelpawel.springbooksproject.user.data.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto {


    @NotNull
    @JsonProperty(namespace = "Title")
    private String title;

    @JsonProperty(namespace = "Plot")
    private String plot;

    @JsonProperty(namespace = "Genre")
    private String genre;

    @JsonProperty(namespace = "Director")
    private String director;

    @NotNull
    @JsonProperty(namespace = "Year")
    private int yearOfRelease;

    @JsonProperty(namespace = "Poster")
    private String poster;

    @ManyToOne
    private User user;
}