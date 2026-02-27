package teamDBMS.sDrumGuitarBE.message.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import teamDBMS.sDrumGuitarBE.message.dto.MessageTemplateListResponse;
import teamDBMS.sDrumGuitarBE.message.entity.MessageTemplate;
import teamDBMS.sDrumGuitarBE.message.dto.CreateMessageTemplateRequest;
import teamDBMS.sDrumGuitarBE.message.dto.MessageTemplateResponse;
import teamDBMS.sDrumGuitarBE.message.entity.MessageType;
import teamDBMS.sDrumGuitarBE.message.repository.MessageTemplateRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageTemplateService {

    private final MessageTemplateRepository messageTemplateRepository;

    public MessageTemplateResponse create(CreateMessageTemplateRequest request) {

        MessageTemplate template =
                new MessageTemplate(
                        request.getType(),
                        request.getTitle(),
                        request.getContent()
                );

        messageTemplateRepository.save(template);

        return MessageTemplateResponse.from(template);
    }

    @Transactional(readOnly = true)
    public MessageTemplateListResponse getTemplates(int page, int size) {

        PageRequest pageable = PageRequest.of(page - 1, size);

        Page<MessageTemplate> result =
                messageTemplateRepository.findAll(pageable);

        List<MessageTemplateResponse> templates =
                result.getContent().stream()
                        .map(MessageTemplateResponse::from)
                        .toList();

        return new MessageTemplateListResponse(
                result.getTotalElements(),
                page,
                size,
                templates
        );
    }

    public MessageTemplateResponse getTemplateByType(MessageType type) {

        MessageTemplate template = messageTemplateRepository.findByType(type)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Message template not found"
                        )
                );

        return MessageTemplateResponse.from(template);
    }

}