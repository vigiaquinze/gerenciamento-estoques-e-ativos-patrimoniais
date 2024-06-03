package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.gerenciamento_senai.Model.Funcionarios;
import br.com.senai.gerenciamento_senai.Repository.FuncionariosRepository;
import lombok.Delegate;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionariosRepository fnR;

    @GetMapping
    public List<Funcionarios> getAllFuncionarios() {
        return (List<Funcionarios>) fnR.findAll();
    }

    @PostMapping
    public Funcionarios adicionarFuncionario(@RequestBody Funcionarios funcionario) {
        return fnR.save(funcionario);
    }

    @GetMapping("/{id}")
    public Optional<Funcionarios> getFuncionarioById(@PathVariable Integer id) {
        return fnR.findById(id);
    }

    @PutMapping("/{id}")
    public Funcionarios updateFuncionarioById(@PathVariable Integer id, @RequestBody Funcionarios funcionario) {
        if (fnR.existsById(id)) {
            funcionario.setRe(id);
            return fnR.save(funcionario);
        } else {
            return null;
        }

    }

    @DeleteMapping("/{id}")
    public List<Funcionarios> deleteFuncionario(@PathVariable Integer id){  
        fnR.deleteById(id);
        return (List<Funcionarios>) fnR.findAll();
    }

}
