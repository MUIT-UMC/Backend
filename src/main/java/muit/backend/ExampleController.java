package muit.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {
    @GetMapping("hy")
    public String hy() {
        return "exampleHtml";
    }
}
