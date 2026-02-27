package teamDBMS.sDrumGuitarBE.message.entity;

public enum TemplateVariable {

    STUDENT_NAME("{학생 이름}"),
    CURRENT_LESSON("{현재 회차}"),
    REMAINING_LESSON("{잔여 회차}"),
    ACADEMY_NAME("{학원명}");

    private final String key;

    TemplateVariable(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}