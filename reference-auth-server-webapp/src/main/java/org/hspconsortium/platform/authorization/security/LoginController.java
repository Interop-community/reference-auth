package org.hspconsortium.platform.authorization.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {

    @RequestMapping({"login", "login/"})
    public String doLoginRedirect() {
        return "redirect:/sso/login" ;
    }

    @RequestMapping(path={"logout", "logout/"}, method = RequestMethod.POST)
    public String doLogoutRedirect() {
        return "redirect:/logout" ;
    }
}