package teamDBMS.sDrumGuitarBE.message.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.message.dto.CreateMessageTemplateRequest;
import teamDBMS.sDrumGuitarBE.message.dto.MessageTemplateResponse;
import teamDBMS.sDrumGuitarBE.message.service.MessageTemplateService;

@RestController
@RequestMapping("/api/messages_templates")
@RequiredArgsConstructor
public class MessageTemplateController {

    private final MessageTemplateService service;

    @PostMapping
    public ResponseEntity<MessageTemplateResponse> create(
            @Valid @RequestBody CreateMessageTemplateRequest request
    ) {
        return ResponseEntity.ok(service.create(request));
    }
}