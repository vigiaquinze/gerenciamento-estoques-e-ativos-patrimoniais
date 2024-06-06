// Define o pacote ao qual a classe pertence
package br.com.senai.gerenciamento_senai.Controller;

// Importa bibliotecas necessárias
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.senai.gerenciamento_senai.Model.Administrador;
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository;
import org.springframework.web.bind.annotation.RequestParam;

// Define a classe como um controlador REST
@RestController

// Define a URL base para este controlador
@RequestMapping("/adm")
public class AdministradorController {

    // Injeta o repositório de administradores
    @Autowired
    private AdministradorRepository admR;

    // Mapeia o método para a URL base e retorna todos os administradores
    @GetMapping
    public List<Administrador> getAllAdministrador() {
        return (List<Administrador>) admR.findAll();
    }

    // Mapeia o método para uma requisição POST e adiciona um novo administrador
    @PostMapping
    public Administrador adicionarAdm(@RequestBody Administrador adm) {
        return admR.save(adm);
    }

    // Mapeia o método para uma requisição GET com um parâmetro ID e retorna o administrador correspondente
    @GetMapping("/{id}")
    public Optional<Administrador> getAdministradorById(@PathVariable Integer id) {
        return admR.findById(id);
    }

    // Mapeia o método para uma requisição PUT com um parâmetro ID e atualiza o administrador correspondente
    @PutMapping("/{id}")
    public Administrador updateAdmById(@PathVariable Integer id, @RequestBody Administrador adm) {
        if (admR.existsById(id)) {
            adm.setId_adm(id);
            return admR.save(adm);
        } else {
            return null;
        }
    }

    // Mapeia o método para uma requisição DELETE com um parâmetro ID e deleta o administrador correspondente
    @DeleteMapping("/{id}")
    public List<Administrador> deleteAdm(@PathVariable Integer id) {
        admR.deleteById(id);
        return (List<Administrador>) admR.findAll();
    }
}
