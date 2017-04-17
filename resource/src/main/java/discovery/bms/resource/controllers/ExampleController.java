package discovery.bms.resource.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ExampleController {


    @GetMapping(value = "secure")
    public ResponseEntity<String> secureMethod() {
        return new ResponseEntity<>("{\"result\": \"Called Secure Method with token.\"}", HttpStatus.OK);
    }


}
