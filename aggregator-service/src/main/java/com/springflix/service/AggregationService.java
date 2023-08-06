package com.springflix.service;

import com.azilzor.springflix.common.Genre;
import com.azilzor.springflix.movie.MovieSearchRequest;
import com.azilzor.springflix.movie.MovieSearchResponse;
import com.azilzor.springflix.movie.MovieServiceGrpc;
import com.azilzor.springflix.user.UserGenreUpdateRequest;
import com.azilzor.springflix.user.UserResponse;
import com.azilzor.springflix.user.UserSearchRequest;
import com.azilzor.springflix.user.UserServiceGrpc;
import com.springflix.dto.MovieDto;
import com.springflix.dto.UserGenreDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregationService {
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userService;
    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieService;

    public List<MovieDto> getMoviesSuggestions(String loginId) {
        UserSearchRequest searchRequest = UserSearchRequest.newBuilder()
                .setLoginId(loginId)
                .build();

        UserResponse userResponse = userService.getUserGenre(searchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
                .setGenre(userResponse.getGenre())
                .build();

        MovieSearchResponse movieServiceMovies = movieService.getMovies(movieSearchRequest);

        return movieServiceMovies.getMoviesList()
                .stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getYear(), movie.getRating()))
                .toList();
    }

    public void setUserGenre(UserGenreDto request) {
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(request.getLoginId())
                .setGenre(Genre.valueOf(request.getGenre()))
                .build();

        UserResponse response = userService.updateUserGenre(userGenreUpdateRequest);

        if (response == null) {
            throw new RuntimeException("Error while updating user genre");
        }
    }
}
