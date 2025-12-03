package org.ukma.spring.crooodle.usersvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.errors.SvcError;
import org.ukma.spring.crooodle.errors.SvcException;
import org.ukma.spring.crooodle.usersvc.dto.ProfileDto;
import org.ukma.spring.crooodle.usersvc.dto.ProfileUpsertDto;
import org.ukma.spring.crooodle.usersvc.entity.ProfileEntity;
import org.ukma.spring.crooodle.usersvc.mapper.ProfileMapper;
import org.ukma.spring.crooodle.usersvc.repository.ProfileRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    public void create(ProfileUpsertDto profileUpsertDto) {
        ProfileEntity entity = profileMapper.profileUpsertDtoToProfileEntity(profileUpsertDto);
        profileRepository.save(entity);
    }

    public ProfileDto read(UUID id) {
        ProfileEntity entity = profileRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new SvcException(SvcError.NOT_FOUND, "Profile not found");
        }
        return profileMapper.profileEntityToProfileDto(entity);
    }

    public void update(UUID id, ProfileUpsertDto profile) {
        ProfileEntity entity = profileRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new SvcException(SvcError.NOT_FOUND, "Profile not found");
        }
        entity = profileMapper.profileUpsertDtoToProfileEntity(profile);
        entity.setId(id);
        profileRepository.save(entity);
    }

    public void delete(UUID id) {
        if (!profileRepository.existsById(id)) {
            throw new SvcException(SvcError.NOT_FOUND, "Profile not found");
        }
        profileRepository.deleteById(id);
    }
}
