package br.com.bakeflow.fidelizacao.controller;

import br.com.bakeflow.fidelizacao.service.FidelizacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fidelizacao")
public class FidelizacaoController {

    private final FidelizacaoService fidelizacaoService;

    public FidelizacaoController(FidelizacaoService fidelizacaoService) {
        this.fidelizacaoService = fidelizacaoService;
    }

    @GetMapping("/relatorio")
    public String listar(Model model) {
        model.addAttribute("fidelizacoes", fidelizacaoService.findAll());
        model.addAttribute("historico", fidelizacaoService.findHistorico());
        model.addAttribute("totalClientes", fidelizacaoService.contarClientesFidelizados());
        model.addAttribute("totalBeneficios", fidelizacaoService.somarBeneficiosGerados());
        model.addAttribute("clienteDestaque", fidelizacaoService.buscarClienteComMaisPontos());
        return "relatorio/listaFidelizacao";
    }
}
