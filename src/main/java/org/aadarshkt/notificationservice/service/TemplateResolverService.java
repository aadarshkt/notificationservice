package org.aadarshkt.notificationservice.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aadarshkt.notificationservice.model.NotificationTemplate;
import org.aadarshkt.notificationservice.repository.NotificationTemplateRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateResolverService {

    private final NotificationTemplateRepository templateRepository;
    private final Configuration freemarkerConfig;

    /**
     * Resolves a template string by eventType and injects data from templateData.
     */
    public String resolveTemplate(String eventType, Map<String, Object> templateData) {
        if (eventType == null) {
            log.warn("Event type is null, cannot resolve template.");
            return "";
        }

        Optional<NotificationTemplate> templateOpt = templateRepository.findByEventType(eventType);
        
        if (templateOpt.isEmpty()) {
            log.warn("No template found for eventType: {}", eventType);
            return "";
        }

        String templateContent = templateOpt.get().getTemplateContent();
        
        try {
            // Using a dynamic string template
            Template t = new Template("name", new StringReader(templateContent), freemarkerConfig);
            StringWriter writer = new StringWriter();
            t.process(templateData, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            log.error("Error processing template for eventType: {}", eventType, e);
            return "";
        }
    }
}
