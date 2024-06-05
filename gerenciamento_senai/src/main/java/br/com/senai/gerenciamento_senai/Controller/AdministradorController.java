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

import br.com.senai.gerenciamento_senai.Model.Administrador;
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository;
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

    @GetMapping("/{id}")
    public Optional<Administrador> getAdministradorById(@PathVariable Integer id) {
        return admR.findById(id);
    }

    @PutMapping("/{id}")
    public Administrador updateAdmById(@PathVariable Integer id, @RequestBody Administrador adm) {
        if (admR.existsById(id)) {
            adm.setId_adm(id);
            return admR.save(adm);
        } else {
            return null;
        }

    }

    @DeleteMapping("/{id}")
    public List<Administrador> deleteAdm(@PathVariable Integer id) {
        admR.deleteById(id);
        return (List<Administrador>) admR.findAll();
    }

}
