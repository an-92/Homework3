package com.example.boarder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permitAll")
//@Api(tags = "PermitAll")  //컨트롤러 이름 변경 기능
public class PermitAllController {

    @GetMapping("/test")
    @ApiOperation(value = "Swagger Test Method Name")   //메서드 이름 작성 기능
    public String permitAllTest(@ApiParam(name = "parameter test") String parameter) {
        return ResponseEntity.ok().body("test").toString();
    }
}

//    컨트롤러로 들어오는 매개변수 ( 파라미터 ) 에 대해 알아보고 싶다면 param 이름 지정
//    public String permitAllTest(@ApiParam(name = "parameter test") String parameter) {
//    이 때 들어가는 기본 값 지정해두고 편하게 사용하고 싶으면 example , value 를 추가 / post 요청의 경우 “string” 이 → “example” 로 변경되게 됩니다.
//    public String permitAllTest(@ApiParam(name = "parameter test" , example = "test" , value = "value")) String parameter) {
