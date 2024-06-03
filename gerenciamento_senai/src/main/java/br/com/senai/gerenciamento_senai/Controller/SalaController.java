package br.com.senai.gerenciamento_senai.Controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.senai.gerenciamento_senai.Model.Salas;
import br.com.senai.gerenciamento_senai.Repository.SalasRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/salas")
public class SalaController {
    @Autowired
    private SalasRepository slR;

    @GetMapping
    public List<Salas> getAllSalas() {
        return (List<Salas>) slR.findAll();
    }

    @PostMapping
    public Salas adicionarNovaSala(@RequestBody Salas salas) {
        return slR.save(salas);
    }

    @GetMapping("/{id}")
    public Optional<Salas> getFuncionarioById(@PathVariable Integer id) {
        return slR.findById(id);
    }
}
