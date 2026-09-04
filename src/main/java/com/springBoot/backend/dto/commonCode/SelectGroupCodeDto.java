package com.springBoot.backend.dto.commonCode;

public class SelectGroupCodeDto {
    public record Req(int groupCode, String name){}
    public record Res(int groupCode, String name){}
}
