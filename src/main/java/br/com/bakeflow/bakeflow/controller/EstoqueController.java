package br.com.bakeflow.bakeflow.controller;

import br.com.bakeflow.bakeflow.model.Estoque;
import br.com.bakeflow.bakeflow.model.Produto;
import br.com.bakeflow.bakeflow.service.EstoqueService;
import br.com.bakeflow.bakeflow.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cadastroEstoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String novo(Model model) {

        if (!model.containsAttribute("estoque")) {
            model.addAttribute("estoque", new Estoque());
        }

        model.addAttribute("produtos", produtoService.findAll());
        return "cadastroEstoque";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Estoque estoque = estoqueService.findById(id);
        if (estoque == null) {
            return "redirect:/cadastroEstoque/relatorio";
        }

        model.addAttribute("estoque", estoque);
        model.addAttribute("produtos", produtoService.findAll());

        return "cadastroEstoque";
    }

    @GetMapping("/relatorio")
    public String listar(Model model) {
        model.addAttribute("lista", estoqueService.findAllWithProduto());
        return "relatorio/listaEstoque";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Estoque estoque,
                         BindingResult result,
                         RedirectAttributes attr) {

        boolean editando = estoque.getIdEstoque() != null;

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.estoque", result);
            attr.addFlashAttribute("estoque", estoque);
            return "redirect:/cadastroEstoque";
        }

        if (estoque.getProduto() == null || estoque.getProduto().getIdProduto() == null) {
            attr.addFlashAttribute("erro", "Selecione um produto!");
            attr.addFlashAttribute("mensagemErro", "Selecione um produto!");
            return "redirect:/cadastroEstoque";
        }

        Long idProduto = estoque.getProduto().getIdProduto();
        Produto produto = produtoService.findById(idProduto);
        estoque.setProduto(produto);


        if (editando) {
            Estoque existente = estoqueService.findById(estoque.getIdEstoque());
            if (existente != null) {

                existente.setQuantidade(estoque.getQuantidade());
                existente.setProduto(produto);

                estoqueService.save(existente);

                attr.addFlashAttribute("mensagem", "Estoque atualizado com sucesso!");
                attr.addFlashAttribute("mensagemSucesso", "Estoque atualizado com sucesso!");
            }

            return "redirect:/cadastroEstoque/relatorio";
        }

        Estoque estoqueExistente = estoqueService.buscarPorProduto(idProduto);

        if (estoqueExistente != null) {
            attr.addFlashAttribute("erro", "Já existe estoque cadastrado para este produto!");
            attr.addFlashAttribute("mensagemErro", "Já existe estoque cadastrado para este produto!");
            return "redirect:/cadastroEstoque";
        }

        estoqueService.save(estoque);
        attr.addFlashAttribute("mensagem", "Estoque cadastrado com sucesso!");
        attr.addFlashAttribute("mensagemSucesso", "Estoque cadastrado com sucesso!");

        return "redirect:/cadastroEstoque";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {

        try {
            estoqueService.delete(id);
            attr.addFlashAttribute("mensagem", "Estoque excluído com sucesso!");
            attr.addFlashAttribute("mensagemSucesso", "Estoque excluído com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao excluir: " + e.getMessage());
            attr.addFlashAttribute("mensagemErro", "Erro ao excluir: " + e.getMessage());
        }

        return "redirect:/cadastroEstoque/relatorio";
    }
}