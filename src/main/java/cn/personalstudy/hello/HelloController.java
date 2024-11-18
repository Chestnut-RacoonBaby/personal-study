package cn.personalstudy.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: qiaohaojie
 * @date: 2024-11-18 19:24:25
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello,world,how are you?";
    }
}
