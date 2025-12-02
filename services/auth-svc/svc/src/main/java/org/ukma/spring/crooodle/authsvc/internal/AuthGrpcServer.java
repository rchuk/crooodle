package org.ukma.spring.crooodle.authsvc.internal;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.ukma.spring.crooodle.authsvc.grpc.AccessToken;
import org.ukma.spring.crooodle.authsvc.grpc.AuthServiceGrpc;
import org.ukma.spring.crooodle.authsvc.grpc.User;
import org.ukma.spring.crooodle.authsvc.mapper.UserGrpcMapper;
import org.ukma.spring.crooodle.authsvc.service.AuthService;

@RequiredArgsConstructor
@GrpcService
public class AuthGrpcServer extends AuthServiceGrpc.AuthServiceImplBase {
	private final UserGrpcMapper userMapper;
	private final AuthService authService;

	@Override
	public void getUser(AccessToken accessToken, StreamObserver<User> responseObserver) {
		var user = authService.getUserEntity(accessToken.getToken());

		responseObserver.onNext(userMapper.userToUserDto(user));
		responseObserver.onCompleted();
	}
}
