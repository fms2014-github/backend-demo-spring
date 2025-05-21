package com.portfolio.backend.dto.commonCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


public class SelectCodeDto {

    @Getter
    public static class Req {
        private int code;
        private int groupCode;
    }

    public record Res(@JsonProperty("code") int idCode, @JsonProperty("groupCode") int idGroupCode, String name) {
    }

}
