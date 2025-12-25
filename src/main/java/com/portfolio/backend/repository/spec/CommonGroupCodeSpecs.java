package com.portfolio.backend.repository.spec;

import com.portfolio.backend.entity.CommonGroupCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class CommonGroupCodeSpecs {

    public static Specification<CommonGroupCode> isGroupCode(int groupCode){
        return (root, query, cb) -> {
            log.info("query: {}", query);
            if(groupCode > 0) {
                return cb.equal(root.get("groupCode"), groupCode);
            }
            return null;
        };
    }
}
