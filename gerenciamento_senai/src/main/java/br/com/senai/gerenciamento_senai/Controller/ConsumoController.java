package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.gerenciamento_senai.Model.Consumo;
import br.com.senai.gerenciamento_senai.Repository.ConsumoRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/consumos")
public class ConsumoController {
    @Autowired
    private ConsumoRepository consumoRepository;

    @GetMapping
    public List<Consumo> getAllConsumos() {
        return (List<Consumo>) consumoRepository.findAll();
    }

    @PostMapping
    public Consumo adicionarConsumo(@RequestBody Consumo consumo) {
        return consumoRepository.save(consumo);
    }

    @GetMapping("/{id}")
    public Optional<Consumo> getConsumoById(@PathVariable Integer id) {
        return consumoRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Consumo updateConsumoById(@PathVariable Integer id, @RequestBody Consumo consumo) {
        if (consumoRepository.existsById(id)) {
            consumo.setId(id);
            return consumoRepository.save(consumo);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public List<Consumo> deleteConsumo(@PathVariable Integer id){  
        consumoRepository.deleteById(id);
        return (List<Consumo>) consumoRepository.findAll();
    }
}
