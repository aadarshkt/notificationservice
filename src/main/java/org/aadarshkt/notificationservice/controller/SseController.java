package org.aadarshkt.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.aadarshkt.notificationservice.service.SseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    @GetMapping("/stream/{userId}")
    public SseEmitter streamEvents(@PathVariable String userId) {
        return sseService.createEmitter(userId);
    }
}
