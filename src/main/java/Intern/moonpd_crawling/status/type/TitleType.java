package Intern.moonpd_crawling.status.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TitleType {
    // lst 유형에서만 사용
    OUT("OUT"), // 안에서 Title을 크롤링 할것인가 (pdf와 함께 크롤링)
    IN("IN"), // 바깥에서 Title을 크롤링 할것인가 (lst와 함께 크롤링)
    
    // single 유형세서만 사용
    NONE("NONE");

    private final String value;

    TitleType(String value) {
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
    public static TitleType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (TitleType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
