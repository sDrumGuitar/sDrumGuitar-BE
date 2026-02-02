package teamDBMS.sDrumGuitarBE.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
