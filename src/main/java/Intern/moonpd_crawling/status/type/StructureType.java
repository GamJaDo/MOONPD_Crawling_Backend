package Intern.moonpd_crawling.status.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StructureType {
    SINGLE_PAGE("SINGLE_PAGE"), // 본 페이지에 다 나오는 경우
    YEAR_FILTERED("YEAR_FILTERED"), // 연도별로 선택을 해야하는 경우
    LISTED_CONTENT("LISTED_CONTENT"), // 글목록 형식으로 되어 있는 경우
    NONE("NONE"); // 기본값 (빈 값 또는 null)

    private final String value;

    StructureType(String value) {
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
    public static StructureType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (StructureType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
