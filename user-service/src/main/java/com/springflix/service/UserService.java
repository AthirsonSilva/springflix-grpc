package com.springflix.service;

import com.azilzor.springflix.common.Genre;
import com.azilzor.springflix.user.UserGenreUpdateRequest;
import com.azilzor.springflix.user.UserResponse;
import com.azilzor.springflix.user.UserSearchRequest;
import com.azilzor.springflix.user.UserServiceGrpc;
import com.springflix.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.transaction.Transactional;

@GrpcService
@AllArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final UserRepository userRepository;

    /**
     * get user genre
     *
     * @param request request
     * @param responseObserver responseObserver
     */
    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        userRepository.findById(request.getLoginId())
                .ifPresent(user -> builder.setName(user.getName())
                        .setLoginId(user.getLogin())
                        .setGenre(Genre.valueOf(user.getGenre())));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().name());

                    builder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre()));
                });

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
