package br.com.bakeflow.fidelizacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fidelizacao")
public class FidelizacaoInfoController {

    @GetMapping("/regras")
    public String regras() {
        return "fidelizacao/regras";
    }
}
