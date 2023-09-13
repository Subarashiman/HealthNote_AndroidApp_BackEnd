package HealthNote.healthnote.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public TestDto TestMapping(){
        return new TestDto(200,"성공");
    }


    @Data
    public static class TestDto{
        private int code;
        private String message;

        public TestDto(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
