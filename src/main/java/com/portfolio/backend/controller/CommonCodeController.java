package com.portfolio.backend.controller;

import com.portfolio.backend.dto.commonCode.InsertCodeDto;
import com.portfolio.backend.dto.commonCode.InsertGroupCodeDto;
import com.portfolio.backend.dto.commonCode.SelectCodeDto;
import com.portfolio.backend.dto.commonCode.SelectGroupCodeDto;
import com.portfolio.backend.entity.CommonCode;
import com.portfolio.backend.entity.CommonGroupCode;
import com.portfolio.backend.exception.DuplicateKeyException;
import com.portfolio.backend.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping("/selectGroupCode")
    public ResponseEntity<List<CommonGroupCode>> selectGroupCode(@RequestBody(required = false) SelectGroupCodeDto.Req req) throws Exception {
        log.info("#### Select group code");
        List<CommonGroupCode> groupCodeList = commonCodeService.selectGroupCode(req);

        return new ResponseEntity<>(groupCodeList, HttpStatus.OK);
    }

    @GetMapping("/selectCode")
    public ResponseEntity<List<SelectCodeDto.Res>> selectCode(@Nullable SelectCodeDto.Req req) throws Exception {
        log.info("#### Select code");
        List<SelectCodeDto.Res> codeList = commonCodeService.selectCode();

        return new ResponseEntity<>(codeList, HttpStatus.OK);
    }

    @PostMapping("/insertCode")
    public ResponseEntity<InsertCodeDto.Res> insertCode(@RequestBody InsertCodeDto.Req req) throws Exception {
        log.info("##### start insertCode");
        log.info("##### commonCode: {}", req);
        InsertCodeDto.Res res;
        int result = commonCodeService.insertCode(req);
        res = new InsertCodeDto.Res(result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/insertGroupCode")
    public ResponseEntity<InsertGroupCodeDto.Res> insertGroupCode(@RequestBody InsertGroupCodeDto.Req req) throws Exception {
        log.info("##### start insertGroupCode");
        log.info("##### commonGroupCode: {}", req);
        int result = commonCodeService.insertGroupCode(req);
        InsertGroupCodeDto.Res res = new InsertGroupCodeDto.Res(result);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/updateGroupCode")
    public ResponseEntity<?> updateGroupCode(@RequestBody CommonGroupCode commonGroupCode) throws Exception {
        log.info("##### start updateGroupCode");
        log.info("##### commonGroupCode: {}", commonGroupCode);
        int result = commonCodeService.updateGroupCode(commonGroupCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
