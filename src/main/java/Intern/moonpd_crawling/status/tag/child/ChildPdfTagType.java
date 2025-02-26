package Intern.moonpd_crawling.status.tag.child;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChildPdfTagType {
    A("A"),         // 앵커 태그
    BUTTON("BUTTON"), // 버튼 태그
    IMG("IMG"),     // 이미지 태그
    DIV("DIV"),
    TD("TD"),
    TR("TR"),// DIV 태그
    SPAN("SPAN"),
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

    ChildPdfTagType(String value) {
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
    public static ChildPdfTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (ChildPdfTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
