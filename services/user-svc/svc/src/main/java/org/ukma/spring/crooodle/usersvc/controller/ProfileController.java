package org.ukma.spring.crooodle.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.dto.ProfileDto;
import org.ukma.spring.crooodle.usersvc.dto.ProfileUpsertDto;
import org.ukma.spring.crooodle.usersvc.service.ProfileService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ProfileDto read(@PathVariable UUID id) {
        return profileService.read(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, ProfileUpsertDto profile) {
        profileService.update(id, profile);
    }
}
