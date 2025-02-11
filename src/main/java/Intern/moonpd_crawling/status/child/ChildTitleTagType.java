package Intern.moonpd_crawling.status.child;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChildTitleTagType {
    P("P"), // p 태그
    SPAN("SPAN"), // span 태그
    STRONG("STRONG"), // strong 태그
    H3("H3"), // h3 태그
    DIV("DIV"), // div 태그
    A("A"), // 앵커 태그
    H5("H5"),
    LI("LI"),
    H2("H2"),
    TR("TR"),
    TD("TD"),
    NONE("NONE"); // 기본값

    private final String value;

    ChildTitleTagType(String value) {
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
    public static ChildTitleTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (ChildTitleTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
