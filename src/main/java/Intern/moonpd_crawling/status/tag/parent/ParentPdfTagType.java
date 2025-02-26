package Intern.moonpd_crawling.status.tag.parent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParentPdfTagType {
    A("A"),         // 앵커 태그
    BUTTON("BUTTON"), // 버튼 태그
    IMG("IMG"),     // 이미지 태그
    DIV("DIV"),     // DIV 태그
    SPAN("SPAN"),   // SPAN 태그
    P("P"), // P 태그
    TD("TD"),
    TR("TR"),
    LI("LI"),
    DD("DD"),
    FORM("FORM"),
    FIGCAPTION("FIGCAPTION"),
    H1("H1"),
    H2("H2"),
    H3("H3"),
    H4("H4"),
    H5("H5"),
    NONE("NONE");   // 기본값 (빈 값 또는 null)

    private final String value;

    ParentPdfTagType(String value) {
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
    public static ParentPdfTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE;
        }
        for (ParentPdfTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
