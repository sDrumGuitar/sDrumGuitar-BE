package teamDBMS.sDrumGuitarBE.message.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.message.dto.CreateMessageTemplateRequest;
import teamDBMS.sDrumGuitarBE.message.dto.MessageTemplateListResponse;
import teamDBMS.sDrumGuitarBE.message.dto.MessageTemplateResponse;
import teamDBMS.sDrumGuitarBE.message.dto.TemplateVariableResponse;
import teamDBMS.sDrumGuitarBE.message.entity.TemplateVariable;
import teamDBMS.sDrumGuitarBE.message.service.MessageTemplateService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/messages/templates")
@RequiredArgsConstructor
public class MessageTemplateController {

    private final MessageTemplateService messageTemplateService;

    @PostMapping
    public ResponseEntity<MessageTemplateResponse> create(
            @Valid @RequestBody CreateMessageTemplateRequest request
    ) {
        return ResponseEntity.ok(messageTemplateService.create(request));
    }

    @GetMapping("/variables")
    public List<TemplateVariableResponse> getVariables() {
        return Arrays.stream(TemplateVariable.values())
                .map(TemplateVariableResponse::from)
                .toList();
    }

    @GetMapping
    public ResponseEntity<MessageTemplateListResponse> getTemplates(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        MessageTemplateListResponse response =
                messageTemplateService.getTemplates(page, size);

        return ResponseEntity.ok(response);
    }
}