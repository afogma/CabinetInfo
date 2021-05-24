package local.clinic1.CabinetInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClinicController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
