package teamDBMS.sDrumGuitarBE.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamDBMS.sDrumGuitarBE.message.entity.MessageTemplate;
import teamDBMS.sDrumGuitarBE.message.entity.MessageType;

import java.util.Optional;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
    Optional<MessageTemplate> findByType(MessageType type);
}