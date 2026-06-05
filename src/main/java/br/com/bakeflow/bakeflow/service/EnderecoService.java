package br.com.bakeflow.bakeflow.service;

import br.com.bakeflow.bakeflow.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EnderecoService {

    private final RestTemplate restTemplate = new RestTemplate();

    //API VIACEP
    public Endereco buscarCep(String cep) {

        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Endereco endereco = new Endereco();
        endereco.setCep(cep);

        if (response != null && !response.containsKey("erro")) {
            endereco.setLogradouro((String) response.get("logradouro"));
            endereco.setBairro((String) response.get("bairro"));
            endereco.setCidade((String) response.get("localidade"));
            endereco.setEstado((String) response.get("uf"));
        }

        return endereco;
    }
}