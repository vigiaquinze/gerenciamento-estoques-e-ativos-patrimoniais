// Define o pacote ao qual a classe pertence
package br.com.senai.gerenciamento_senai.Controller;

// Importa bibliotecas necessárias
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

// Define a classe como um controlador REST
@RestController

// Define a URL base para este controlador
@RequestMapping("/funcionarios")
public class FuncionarioController {

    // Injeta o repositório de funcionários
    @Autowired
    private FuncionariosRepository fnR;

    // Mapeia o método para a URL base e retorna todos os funcionários
    @GetMapping
    public List<Funcionarios> getAllFuncionarios() {
        return (List<Funcionarios>) fnR.findAll();
    }

    // Mapeia o método para uma requisição POST e adiciona um novo funcionário
    @PostMapping
    public Funcionarios adicionarFuncionario(@RequestBody Funcionarios funcionario) {
        return fnR.save(funcionario);
    }

    // Mapeia o método para uma requisição GET com um parâmetro ID e retorna o funcionário correspondente
    @GetMapping("/{id}")
    public Optional<Funcionarios> getFuncionarioById(@PathVariable Integer id) {
        return fnR.findById(id);
    }

    // Mapeia o método para uma requisição PUT com um parâmetro ID e atualiza o funcionário correspondente
    @PutMapping("/{id}")
    public Funcionarios updateFuncionarioById(@PathVariable Integer id, @RequestBody Funcionarios funcionario) {
        if (fnR.existsById(id)) {
            funcionario.setRe(id);
            return fnR.save(funcionario);
        } else {
            return null;
        }
    }

    // Mapeia o método para uma requisição DELETE com um parâmetro ID e deleta o funcionário correspondente
    @DeleteMapping("/{id}")
    public List<Funcionarios> deleteFuncionario(@PathVariable Integer id){  
        fnR.deleteById(id);
        return (List<Funcionarios>) fnR.findAll();
    }
}
