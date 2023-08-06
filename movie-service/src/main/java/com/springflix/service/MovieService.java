package com.springflix.service;

import com.azilzor.springflix.movie.MovieDto;
import com.azilzor.springflix.movie.MovieSearchRequest;
import com.azilzor.springflix.movie.MovieSearchResponse;
import com.azilzor.springflix.movie.MovieServiceGrpc;
import com.springflix.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@AllArgsConstructor
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {
    private final MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList = movieRepository.getMovieByGenreOrderByYearDesc(request.getGenre().name())
                .stream().map(
                        movie -> MovieDto.newBuilder()
                                .setId(movie.getId())
                                .setTitle(movie.getTitle())
                                .setYear(movie.getYear())
                                .setRating(movie.getRating())
                                .build()
                ).toList();

        MovieSearchResponse response = MovieSearchResponse.newBuilder()
                .addAllMovies(movieDtoList).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
