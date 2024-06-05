package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.senai.gerenciamento_senai.Model.Movimentacao;
import br.com.senai.gerenciamento_senai.Repository.MovimentacaoRepository;

@Controller
@RequestMapping("/movimentacao")
public class MovimentacaoController {
    @Autowired
    MovimentacaoRepository mvR;

    @GetMapping
    public List<Movimentacao> getAllMovimentacao() {
        return (List<Movimentacao>) mvR.findAll();
    }

    @PostMapping
    public Movimentacao adicionarMovimentacao(@RequestBody Movimentacao movimentacao) {
        return mvR.save(movimentacao);
    }

    @GetMapping("/{id}")
    public Optional<Movimentacao> getMovimentacaoById(@PathVariable Integer id) {
        return mvR.findById(id);
    }

    @PutMapping("/{id}")
    public Movimentacao updateMovimentacaoById(@PathVariable Integer id, @RequestBody Movimentacao movimentacao) {
        if (mvR.existsById(id)) {
            movimentacao.setId_mov(id);
            return mvR.save(movimentacao);
        } else {
            return null;
        }

    }

    @DeleteMapping("/{id}")
    public List<Movimentacao> deleteFuncionario(@PathVariable Integer id){  
        mvR.deleteById(id);
        return (List<Movimentacao>) mvR.findAll();
    }
}
