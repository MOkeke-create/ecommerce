package org.yearup.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService,
                             UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal) {

        User user = userService.getByUserName(principal.getName());

        return profileService.getByUserId(user.getId())
                .map(ResponseEntity::ok)                 // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 if profile doesn't exist
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(
            Principal principal,
            @RequestBody Profile profile) {

        User user = userService.getByUserName(principal.getName());

        return profileService.update(user.getId(), profile)
                .map(ResponseEntity::ok)                 // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 if profile doesn't exist
    }

}
