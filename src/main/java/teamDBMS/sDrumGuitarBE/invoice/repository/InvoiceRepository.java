package teamDBMS.sDrumGuitarBE.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByStudentIdOrderByIssuedAtDesc(Long studentId);

}
