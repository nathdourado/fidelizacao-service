package br.com.bakeflow.bakeflow.controller;

import br.com.bakeflow.bakeflow.model.Produto;
import br.com.bakeflow.bakeflow.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cadastroProduto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String novo(Model model) {
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new Produto());
        }
        return "cadastroProduto";
    }

    @GetMapping("/relatorio")
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.findAll());
        return "relatorio/listaProduto";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Produto produto,
                         BindingResult result,
                         RedirectAttributes attr) {

        boolean editando = (produto.getIdProduto() != null);

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.produto", result);
            attr.addFlashAttribute("produto", produto);
            return "redirect:/cadastroProduto";
        }

        produtoService.save(produto);

        attr.addFlashAttribute("mensagem",
                editando ? "Produto atualizado com sucesso!" : "Produto cadastrado com sucesso!");
        attr.addFlashAttribute("mensagemSucesso",
                editando ? "Produto atualizado com sucesso!" : "Produto cadastrado com sucesso!");

        return "redirect:/cadastroProduto";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Produto produto = produtoService.findById(id);
        if (produto == null) {
            return "redirect:/cadastroProduto/relatorio";
        }

        model.addAttribute("produto", produto);
        return "cadastroProduto";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {

        try {
            produtoService.delete(id);
            attr.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
            attr.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso!");

        } catch (RuntimeException ex) {
            attr.addFlashAttribute("erro", ex.getMessage());
            attr.addFlashAttribute("mensagemErro", ex.getMessage());
        }

        return "redirect:/cadastroProduto/relatorio";
    }
}