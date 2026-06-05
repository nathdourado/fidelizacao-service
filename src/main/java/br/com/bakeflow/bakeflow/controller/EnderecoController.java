package br.com.bakeflow.bakeflow.controller;

import br.com.bakeflow.bakeflow.model.Endereco;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enderecoCliente")
public class EnderecoController {


    public EnderecoController() {
    }


    @GetMapping
    public String showForm(Model model) {
        if (!model.containsAttribute("endereco")) {
            model.addAttribute("endereco", new Endereco());
        }
        return "cadastroEndereco";
    }

    @PostMapping
    @Operation(summary = "endpoint para cadastro de endereco")
    public String submitForm(@Valid Endereco endereco, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.endereco", result);
            attributes.addFlashAttribute("endereco", endereco);
            return "redirect:/cadastroCliente";
        }


        attributes.addFlashAttribute("mensagem", "Endereco cadastrado com sucesso!");
        attributes.addFlashAttribute("mensagemSucesso", "Endereco cadastrado com sucesso!");
        return "redirect:/cadastroCliente";
    }
}