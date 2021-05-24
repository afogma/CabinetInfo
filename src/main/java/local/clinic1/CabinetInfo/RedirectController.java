package local.clinic1.CabinetInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
    /*
     * Redirects all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
     */
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        return "forward:/";
    }
}