package com.portfolio.backend.dto.commonCode;

import com.portfolio.backend.entity.CommonCode;
import com.portfolio.backend.entity.CommonCodeId;

public class InsertCodeDto {

    public record Req(int code, int groupCode, String name) {

        public CommonCode toCommonCodeEntity() {
            CommonCodeId entityId = new CommonCodeId();
            entityId.setCode(code);
            entityId.setGroupCode(groupCode);

            CommonCode entity = new CommonCode();
            entity.setId(entityId);
            entity.setName(name);
            return entity;
        }
    }

    public record Res(int result){}
}
