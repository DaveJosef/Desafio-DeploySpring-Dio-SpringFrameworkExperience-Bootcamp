package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("hello")
@Api(tags = "Hello Controller")
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, Digital Innovation One!";
    }
}
