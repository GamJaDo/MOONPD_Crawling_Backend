package Intern.moonpd_crawling.status.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TagType {
    A("A"),
    BUTTON("BUTTON"),
    IMG("IMG"),
    DIV("DIV"),
    TD("TD"),
    TR("TR"),
    TABLE("TABLE"),
    SPAN("SPAN"),
    LI("LI"),
    DD("DD"),
    FORM("FORM"),
    ARTICLE("ARTICLE"),
    H1("H1"),
    H2("H2"),
    H3("H3"),
    H4("H4"),
    H5("H5"),
    FIGURE("FIGURE"),
    NAV("NAV"),
    UL("UL"),
    P("P"),
    FIGCAPTION("FIGCAPTION"),
    STRONG("STRONG"),
    EM("EM"),
    B("B"),
    DT("DT"),
    SECTION("SECTION"),
    SELECT("SELECT"),
    NONE("NONE");

    private final String value;

    TagType(String value) {
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
    public static TagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (TagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
