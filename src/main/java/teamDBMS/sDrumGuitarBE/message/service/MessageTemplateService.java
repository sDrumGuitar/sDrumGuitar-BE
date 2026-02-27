package teamDBMS.sDrumGuitarBE.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamDBMS.sDrumGuitarBE.message.entity.MessageTemplate;
import teamDBMS.sDrumGuitarBE.message.dto.CreateMessageTemplateRequest;
import teamDBMS.sDrumGuitarBE.message.dto.MessageTemplateResponse;
import teamDBMS.sDrumGuitarBE.message.repository.MessageTemplateRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageTemplateService {

    private final MessageTemplateRepository repository;

    public MessageTemplateResponse create(CreateMessageTemplateRequest request) {

        MessageTemplate template =
                new MessageTemplate(
                        request.getType(),
                        request.getTitle(),
                        request.getContent()
                );

        repository.save(template);

        return MessageTemplateResponse.from(template);
    }
}