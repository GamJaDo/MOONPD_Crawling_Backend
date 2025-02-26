package Intern.moonpd_crawling.status.tag.parent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParentYearTagType {
    A("A"), // 앵커 태그
    DIV("DIV"),
    TD("TD"),
    LI("LI"),
    UL("UL"),
    FORM("FORM"),
    ARTICLE("ARTICLE"),
    FIGCAPTION("FIGCAPTION"),
    NONE("NONE"); // 기본값으로 사용할 항목

    private final String value;

    ParentYearTagType(String value) {
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
    public static ParentYearTagType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (ParentYearTagType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
