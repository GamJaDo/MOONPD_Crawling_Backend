package Intern.moonpd_crawling.status.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LinkType {
    ONCLICK_LINK("ONCLICK_LINK"),  // onclick이 있는 경우
    HREF_LINK("HREF_LINK"),    // onclick이 없는 경우
    PSEUDO_LINK("PSEUDO_LINK"), // 실제 URL 대신에 자리만 차지하는 "의사 링크"
    JAVASCRIPT_LINK("JAVASCRIPT_LINK"), // href 안에 js가 들어있는 경우
    OPTION_LINK("OPTION_LINK"),  // idx가 있으면
    NONE("NONE");

    private final String value;

    LinkType(String value) {
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
    public static LinkType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return NONE; // null 또는 빈 문자열에 대해 기본값 반환
        }
        for (LinkType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type; // 일치하는 값 반환
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
