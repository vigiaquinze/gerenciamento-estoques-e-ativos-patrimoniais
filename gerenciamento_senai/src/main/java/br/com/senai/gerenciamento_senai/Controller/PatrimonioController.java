package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.gerenciamento_senai.Model.Funcionarios;
import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{id}")
    public Optional<Patrimonio> getPatrimonioById(@PathVariable Integer id) {
        return ptR.findById(id);
    }

    @PutMapping("/{id}")
    public Patrimonio updatePatrimonioById(@PathVariable Integer id, @RequestBody Patrimonio patrimonio) {
        if (ptR.existsById(id)) {
            patrimonio.setId_patrimonio(id);
            return ptR.save(patrimonio);
        } else {
            return null;
        }

    }

    @DeleteMapping("/{id}")
    public List<Patrimonio> deletePatrimonio(@PathVariable Integer id) {
        ptR.deleteById(id);
        return (List<Patrimonio>) ptR.findAll();
    }
}
