package com.springBoot.backend.restController;

import com.springBoot.backend.dto.commonCode.InsertCodeDto;
import com.springBoot.backend.dto.commonCode.InsertGroupCodeDto;
import com.springBoot.backend.dto.commonCode.SelectCodeDto;
import com.springBoot.backend.dto.commonCode.SelectGroupCodeDto;
import com.springBoot.backend.entity.CommonGroupCode;
import com.springBoot.backend.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping("/selectGroupCode")
    public ResponseEntity<List<CommonGroupCode>> selectGroupCode(@ModelAttribute SelectGroupCodeDto.Req req) throws Exception {
        log.debug("#### Select group code");
        List<CommonGroupCode> groupCodeList = commonCodeService.selectGroupCode(req);

        return new ResponseEntity<>(groupCodeList, HttpStatus.OK);
    }

    @GetMapping("/selectCode")
    public ResponseEntity<List<SelectCodeDto.Res>> selectCode(@ModelAttribute SelectCodeDto.Req req) throws Exception {
        log.debug("#### Select code");
        List<SelectCodeDto.Res> codeList = commonCodeService.selectCode();

        return new ResponseEntity<>(codeList, HttpStatus.OK);
    }

    @PostMapping("/insertCode")
    public ResponseEntity<InsertCodeDto.Res> insertCode(@RequestBody InsertCodeDto.Req req) throws Exception {
        log.debug("##### start insertCode");
        log.debug("##### commonCode: {}", req);
        InsertCodeDto.Res res;
        int result = commonCodeService.insertCode(req);
        res = new InsertCodeDto.Res(result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/insertGroupCode")
    public ResponseEntity<InsertGroupCodeDto.Res> insertGroupCode(@RequestBody InsertGroupCodeDto.Req req) throws Exception {
        log.debug("##### start insertGroupCode");
        log.debug("##### commonGroupCode: {}", req);
        int result = commonCodeService.insertGroupCode(req);
        InsertGroupCodeDto.Res res = new InsertGroupCodeDto.Res(result);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/updateGroupCode")
    public ResponseEntity<?> updateGroupCode(@RequestBody CommonGroupCode commonGroupCode) throws Exception {
        log.debug("##### start updateGroupCode");
        log.debug("##### commonGroupCode: {}", commonGroupCode);
        int result = commonCodeService.updateGroupCode(commonGroupCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
