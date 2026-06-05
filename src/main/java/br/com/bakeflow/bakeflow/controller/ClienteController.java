package br.com.bakeflow.bakeflow.controller;

import br.com.bakeflow.bakeflow.model.Endereco;
import br.com.bakeflow.bakeflow.service.ClienteService;
import br.com.bakeflow.bakeflow.service.EnderecoService;
import jakarta.validation.Valid;
import br.com.bakeflow.bakeflow.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cadastroCliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public String novoCliente(Model model) {

        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }

        return "cadastroCliente";
    }


    @GetMapping("/relatorio")
    public String listar(Model model) {
        model.addAttribute("lista", clienteService.findAll());
        return "/relatorio/listaCliente";
    }

    @PostMapping
    public String submitForm(@Valid Cliente cliente,
                             BindingResult result,
                             RedirectAttributes attributes) {

        if (result.hasErrors()) {

            attributes.addFlashAttribute("org.springframework.validation.BindingResult.cliente", result);
            attributes.addFlashAttribute("cliente", cliente);

            return "redirect:/cadastroCliente";
        }

        Endereco apiEndereco = enderecoService.buscarCep(cliente.getEndereco().getCep());
        cliente.getEndereco().setLogradouro(apiEndereco.getLogradouro());
        cliente.getEndereco().setBairro(apiEndereco.getBairro());
        cliente.getEndereco().setCidade(apiEndereco.getCidade());
        cliente.getEndereco().setEstado(apiEndereco.getEstado());

        boolean editando = cliente.getIdCliente() != null;

        clienteService.save(cliente);

        if (editando) {
            attributes.addFlashAttribute("mensagemSucesso", "Cliente atualizado com sucesso!");
        } else {
            attributes.addFlashAttribute("mensagemSucesso", "Cliente cadastrado com sucesso!");
        }

        return "redirect:/cadastroCliente";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            return "redirect:/cadastroCliente/relatorio";
        }

        model.addAttribute("cliente", cliente);
        return "cadastroCliente";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            clienteService.excluir(id);
            attributes.addFlashAttribute("mensagemSucesso", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }

        return "redirect:/cadastroCliente/relatorio";
    }

}