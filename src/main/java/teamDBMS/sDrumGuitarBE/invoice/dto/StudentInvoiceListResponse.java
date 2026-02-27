package teamDBMS.sDrumGuitarBE.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudentInvoiceListResponse {

        private Long studentId;
        private List<StudentInvoiceItem> items;

}