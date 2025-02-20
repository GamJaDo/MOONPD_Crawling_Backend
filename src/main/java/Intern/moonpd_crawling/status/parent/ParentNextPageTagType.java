package Intern.moonpd_crawling.status.parent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParentNextPageTagType {
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
    ARTICLE("ARTICLE"),
    NONE("NONE");   // 기본값 (빈 값 또는 null)

    private final String value;

    ParentNextPageTagType(String value) {
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
    public static ParentNextPageTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE;
        }
        for (ParentNextPageTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
