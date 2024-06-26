package br.com.senai.gerenciamento_senai.Controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.senai.gerenciamento_senai.Model.Administrador;
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository;
import br.com.senai.gerenciamento_senai.Repository.VerificaCadastroAdmRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/adm")
public class AdministradorController {
    @Autowired
    private AdministradorRepository admR;



    @GetMapping
    public List<Administrador> getAllAdministrador() {
        return (List<Administrador>) admR.findAll();
    }

    @PostMapping
    public Administrador adicionarAdm(@RequestBody Administrador adm) {
        return admR.save(adm);
    }

    @GetMapping("/{cpf}")
    public Optional<Administrador> getAdministradorById(@PathVariable String cpf) {
        return admR.findById(cpf);
    }

    @PutMapping("/{cpf}")
    public Administrador updateAdmById(@PathVariable String cpf, @RequestBody Administrador adm) {
        if (admR.existsById(cpf)) {
            adm.setCpf(cpf);
            return admR.save(adm);
        } else {
            return null;
        }

    }

    @DeleteMapping("/{cpf}")
    public List<Administrador> deleteAdm(@PathVariable String cpf) {
        admR.deleteById(cpf);
        return (List<Administrador>) admR.findAll();
    }

}
