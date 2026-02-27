package teamDBMS.sDrumGuitarBE.invoice.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamDBMS.sDrumGuitarBE.invoice.dto.StudentInvoiceListResponse;
import teamDBMS.sDrumGuitarBE.invoice.service.InvoiceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentInvoiceListResponse> getStudentInvoices(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                invoiceService.getStudentInvoices(studentId)
        );
    }
}