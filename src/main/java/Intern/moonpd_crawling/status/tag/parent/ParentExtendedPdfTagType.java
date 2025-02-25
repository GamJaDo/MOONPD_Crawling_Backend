package Intern.moonpd_crawling.status.tag.parent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParentExtendedPdfTagType {
    A("A"),         // 앵커 태그
    BUTTON("BUTTON"), // 버튼 태그
    IMG("IMG"),     // 이미지 태그
    DIV("DIV"),     // DIV 태그
    SPAN("SPAN"),   // SPAN 태그
    P("P"), // P 태그
    TD("TD"),
    TR("TR"),
    LI("LI"),
    UL("UL"),
    FORM("FORM"),
    ARTICLE("ARTICLE"),
    TBODY("TBODY"),
    FIGCAPTION("FIGCAPTION"),
    TABLE("TABLE"),
    NONE("NONE");                // 기본값 (빈 값 또는 null)

    private final String value;

    ParentExtendedPdfTagType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toLowerCase();
    }

    @JsonCreator
    public static ParentExtendedPdfTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (ParentExtendedPdfTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
