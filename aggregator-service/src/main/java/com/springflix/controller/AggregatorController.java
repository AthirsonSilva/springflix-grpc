package com.springflix.controller;

import com.springflix.dto.MovieDto;
import com.springflix.dto.UserGenreDto;
import com.springflix.service.AggregationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aggregator")
@AllArgsConstructor
public class AggregatorController {
    private AggregationService aggregationService;

    @GetMapping("/user/{loginId}")
    public List<MovieDto> getMovies(@PathVariable String loginId) {
        return aggregationService.getMoviesSuggestions(loginId);
    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenreDto request) {
        aggregationService.setUserGenre(request);
    }
}
