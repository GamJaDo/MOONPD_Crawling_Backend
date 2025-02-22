package Intern.moonpd_crawling.status.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum YearType {
    HAS_ONCLICK("HAS_ONCLICK"),  // onclick이 있는 경우
    NO_ONCLICK("NO_ONCLICK"),    // onclick이 없는 경우
    PSEUDO_LINK("PSEUDO_LINK"),
    NONE("NONE");                // 기본값 (빈 값 또는 null)

    private final String value;

    YearType(String value) {
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
    public static YearType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (YearType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
