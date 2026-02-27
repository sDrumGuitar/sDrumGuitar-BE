package teamDBMS.sDrumGuitarBE.message.entity;

public enum MessageType {
    ATTENDANCE,   // 출석
    LATE,         // 지각
    ABSENT,       // 결석
    MAKEUP,       // 보강
    ROLLOVER,     // 이월
    INVOICE_UNPAID, // 미납
    CUSTOM        // 직접 작성
}