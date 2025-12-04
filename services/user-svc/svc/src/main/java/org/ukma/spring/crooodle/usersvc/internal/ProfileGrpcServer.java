package org.ukma.spring.crooodle.usersvc.internal;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.ukma.spring.crooodle.usersvc.grpc.*;
import org.ukma.spring.crooodle.usersvc.mapper.ProfileGrpcMapper;
import org.ukma.spring.crooodle.usersvc.service.ProfileService;

@RequiredArgsConstructor
@GrpcService
public class ProfileGrpcServer extends ProfileServiceGrpc.ProfileServiceImplBase{
    private final ProfileGrpcMapper profileGrpcMapper;
    private final ProfileService profileService;

    @Override
    public void createProfile(ProfileUpsert profileUpsert, StreamObserver<Empty> responseObserver) {
        var profileUpsertDto = profileGrpcMapper.profileUpsertToProfileUpsertDto(profileUpsert);
        profileService.create(profileUpsertDto);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void readProfile(UUID uuid, StreamObserver<Profile> responseObserver) {
        var profile = profileService.read(java.util.UUID.fromString(uuid.getId()));
        responseObserver.onNext(profileGrpcMapper.profileDtoToProfile(profile));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProfile(UUID uuid, StreamObserver<Empty> responseObserver) {
        profileService.delete(java.util.UUID.fromString(uuid.getId()));
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
