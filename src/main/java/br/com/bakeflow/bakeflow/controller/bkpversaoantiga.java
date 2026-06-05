package br.com.bakeflow.bakeflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.bakeflow.bakeflow.model.Pedido;
import br.com.bakeflow.bakeflow.repository.PedidoRepository;

@Controller
public class bkpversaoantiga {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/cadastrarPedido")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("cadastro/formPedido");
        mv.addObject("pedido", new Pedido());
        return mv;
    }

    @RequestMapping(value = "/cadastrarPedido", method = RequestMethod.POST)
    @Operation(summary = "endpoint para cadastro de pedido")
    public String form(@Valid Pedido pedido, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            attributes.addFlashAttribute("mensagemErro", "Verifique os campos...");
            return "redirect:/cadastrarPedido";
        }

        pedidoRepository.save(pedido);
        attributes.addFlashAttribute("mensagem", "Pedido cadastrado com sucesso!");
        attributes.addFlashAttribute("mensagemSucesso", "Pedido cadastrado com sucesso!");
        return "redirect:/cadastrarPedido";
    }
}