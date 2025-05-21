package com.portfolio.backend.repository.spec;

import com.portfolio.backend.dto.commonCode.SelectGroupCodeDto;
import com.portfolio.backend.entity.CommonGroupCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class CommonGroupCodeSpecs {

    public static Specification<CommonGroupCode> isGroupCode(SelectGroupCodeDto.Req req){
        return (root, query, cb) -> {
            log.info("query: {}", query);
            if(req != null && req.groupCode() > 0) {
                return cb.equal(root.get("groupCode"), req.groupCode());
            }
            return null;
        };
    }
}
