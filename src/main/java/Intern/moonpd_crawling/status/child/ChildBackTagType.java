package Intern.moonpd_crawling.status.child;

import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChildBackTagType {
    A("A"), // 앵커 태그
    DIV("DIV"),
    BUTTON("BUTTON"),
    NONE("NONE"); // 기본값으로 사용할 항목

    private final String value;

    ChildBackTagType(String value) {
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
    public static ChildBackTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (ChildBackTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
