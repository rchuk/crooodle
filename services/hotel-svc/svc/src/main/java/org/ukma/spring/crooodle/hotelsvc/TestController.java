package org.ukma.spring.crooodle.hotelsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.usersvc.client.UserSvcClient;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final UserSvcClient userSvc;

    @GetMapping("/test")
    String test() {
        return "Wow it works! " + userSvc.test();
    }
}
