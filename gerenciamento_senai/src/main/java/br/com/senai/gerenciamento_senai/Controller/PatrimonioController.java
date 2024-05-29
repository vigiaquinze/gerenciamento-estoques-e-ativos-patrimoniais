package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/ativos")
public class PatrimonioController {

    @Autowired
    private PatrimonioRepository ptR;

    @GetMapping
    public List<Patrimonio> getAllPatrimonios() {
        return (List<Patrimonio>) ptR.findAll();
    }

    @PostMapping
    public Patrimonio adicionarNovoPatrimonio(@RequestBody Patrimonio patrimonio) {
        return ptR.save(patrimonio);
    }

}
