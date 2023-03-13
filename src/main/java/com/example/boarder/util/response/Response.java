package com.example.boarder.util.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

//앞단 ( React ) 와 Ajax 통신을 할 때 공통적인 결과값으로 받아서 Exception 처리와 같은 공통적으로 처리해야하는 부분을 쉽게 하기 위해서 입니다.
//Response 객체는 Builder 패턴을 사용해서 ResponseEntity 의 구성요소를 포함한 형태로 만든 것을 사용
@Getter
@ToString
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 333444555666777888L;
    private final String message; //결과메세지
    private final int code;     //결과코드
    private final int total;    //총 응답 데이터 수
    private final T data;       //응답 데이터
    private final HttpStatus status;

    public static class ResponseBuilder<T> {
        private final String message;   //결과메세지
        private final int code;     //int 형 코드
        private int total;      //총 응답 데이터 수
        private T data;         //응답 데이터
        private HttpStatus status;

        private void setStatus(int code) {
            try {
                status = HttpStatus.valueOf(code);
            } catch (IllegalArgumentException ignored) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            };
        }

        private ResponseBuilder(String message, int code) {
            this.message = message;
            this.code = code;
            setStatus(code);
        }

        public ResponseBuilder<T> data(T value) {
            data = value;
            return this;
        }

        public ResponseBuilder<T> total(int value) {
            total = value;
            return this;
        }

        public Response<T> build() {
            return new Response<T>(this);
        }
    }

    public static <T> ResponseBuilder<T> builder(String message, int code) {
        return new ResponseBuilder<T>(message, code);
    }

    private Response(ResponseBuilder<T> responseBuilder) {
        message = responseBuilder.message;
        status = responseBuilder.status;
        total = responseBuilder.total;
        data = responseBuilder.data;
        code = responseBuilder.code;
    }


}
