package com.portfolio.backend.dto.commonCode;

import com.portfolio.backend.entity.CommonGroupCode;

public class InsertGroupCodeDto {

    public record Req(int groupCode, String name) {

        public CommonGroupCode toCommonGroupCodeEntity() {
            CommonGroupCode commonGroupCode = new CommonGroupCode();
            commonGroupCode.setGroupCode(groupCode);
            commonGroupCode.setName(name);
            return commonGroupCode;
        }
    }

    public record Res(int result){}
}
