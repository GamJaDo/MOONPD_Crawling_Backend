package Intern.moonpd_crawling.status.selector.extended;

import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExtendedPdfSelectorType {
    CLASS("CLASS"),
    STYLE("STYLE"),
    ID("ID"),
    NONE("NONE");

    private final String value;

    ExtendedPdfSelectorType(String value) {
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
    public static ExtendedPdfSelectorType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (ExtendedPdfSelectorType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
