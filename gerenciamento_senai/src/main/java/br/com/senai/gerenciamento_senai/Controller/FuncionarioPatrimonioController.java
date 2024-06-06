package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository;

@RestController
@RequestMapping("/funcionarios-gerenciamento-patrimonios")
public class FuncionarioPatrimonioController {

    @Autowired
    private PatrimonioRepository patrimonioRepository;

    // Método para listar todos os patrimônios
    @GetMapping
    public List<Patrimonio> getAllPatrimonios() {
        // Recupera todos os patrimônios do repositório
        return (List<Patrimonio>) patrimonioRepository.findAll();
    }

    // Método para adicionar um novo patrimônio
    @PostMapping
    public Patrimonio adicionarPatrimonio(@RequestBody Patrimonio patrimonio) {
        // Salva o novo patrimônio no repositório
        return patrimonioRepository.save(patrimonio);
    }

    // Método para buscar um patrimônio por ID
    @GetMapping("/{id}")
    public Optional<Patrimonio> getPatrimonioById(@PathVariable Integer id) {
        // Recupera um patrimônio pelo ID fornecido
        return patrimonioRepository.findById(id);
    }

    // Método para atualizar um patrimônio existente
    @PutMapping("/{id}")
    public Patrimonio updatePatrimonioById(@PathVariable Integer id, @RequestBody Patrimonio patrimonio) {
        // Verifica se o patrimônio existe
        if (patrimonioRepository.existsById(id)) {
            // Define o ID do patrimônio para garantir que ele será atualizado
            patrimonio.setId(id);
            // Salva o patrimônio atualizado no repositório
            return patrimonioRepository.save(patrimonio);
        } else {
            // Retorna null se o patrimônio não existir
            return null;
        }
    }

    // Método para deletar um patrimônio
    @DeleteMapping("/{id}")
    public List<Patrimonio> deletePatrimonio(@PathVariable Integer id) {
        // Deleta o patrimônio pelo ID fornecido
        patrimonioRepository.deleteById(id);
        // Retorna a lista atualizada de patrimônios
        return (List<Patrimonio>) patrimonioRepository.findAll();
    }
}
