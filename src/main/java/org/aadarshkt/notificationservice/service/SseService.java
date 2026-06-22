package org.aadarshkt.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

    // TODO: Think how would multiple instances of notification service would send sse events.
    // Currently this is an in-memory map which works for a single instance.
    private final Map<String, SseEmitter> activeEmitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(String userId) {
        // Set timeout to 0 (no timeout) or a high value
        SseEmitter emitter = new SseEmitter(0L);
        activeEmitters.put(userId, emitter);

        emitter.onCompletion(() -> {
            log.debug("SSE completion callback for user: {}", userId);
            activeEmitters.remove(userId);
        });

        emitter.onTimeout(() -> {
            log.debug("SSE timeout callback for user: {}", userId);
            emitter.complete();
            activeEmitters.remove(userId);
        });

        emitter.onError(e -> {
            log.debug("SSE error callback for user: {}, error: {}", userId, e.getMessage());
            activeEmitters.remove(userId);
        });

        return emitter;
    }

    public boolean sendEventToUser(String userId, Object payload) {
        SseEmitter emitter = activeEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(payload));
                return true;
            } catch (IOException e) {
                log.error("Error sending SSE to user: {}", userId, e);
                emitter.completeWithError(e);
                activeEmitters.remove(userId);
            }
        }
        return false;
    }
}
