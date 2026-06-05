package br.com.bakeflow.bakeflow.controller;

import br.com.bakeflow.bakeflow.service.RankingProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Index")
public class HomeController {

    private final RankingProdutoService rankingProdutoService;

    public HomeController(RankingProdutoService rankingProdutoService) {
        this.rankingProdutoService = rankingProdutoService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("topProdutos", rankingProdutoService.buscarTop3ProdutosMaisVendidos());
        return "Index";
    }
}