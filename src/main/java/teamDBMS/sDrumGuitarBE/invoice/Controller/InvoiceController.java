package teamDBMS.sDrumGuitarBE.invoice.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceUpdateRequest;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceUpdateResponse;
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

    @PatchMapping("/{invoiceId}")
    public ResponseEntity<InvoiceUpdateResponse> updateInvoice(
            @PathVariable Long invoiceId,
            @RequestBody @Valid InvoiceUpdateRequest request
    ) {
        return ResponseEntity.ok(
                invoiceService.updateInvoice(invoiceId, request)
        );
    }
}