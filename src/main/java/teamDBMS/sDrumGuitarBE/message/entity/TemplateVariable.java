package teamDBMS.sDrumGuitarBE.message.entity;

public enum TemplateVariable {

    STUDENT_NAME("studentName"),
    CURRENT_LESSON("currentLesson"),
    REMAINING_LESSON("remainingLesson"),
    ACADEMY_NAME("academyName");

    private final String key;

    TemplateVariable(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}