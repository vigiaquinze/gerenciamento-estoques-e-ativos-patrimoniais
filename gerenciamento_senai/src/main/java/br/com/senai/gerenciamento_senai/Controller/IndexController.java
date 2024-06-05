package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository;
import br.com.senai.gerenciamento_senai.Repository.FuncionariosRepository;
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository;

@Controller
public class IndexController {

    @Autowired
    private FuncionariosRepository fnR;

    @Autowired
    private PatrimonioRepository ptR;

    private boolean acessoFuncionario = false;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha) {
        if (fnR.existsByEmail(email)) {
            try {
                if (fnR.findByEmail(email).getSenha().trim().equals(senha.trim())) {
                    acessoFuncionario = true;
                    return "redirect:/interna";
                } else {
                    return "Senha Incorreta";
                }
            } catch (Exception e) {
                System.out.println(e);
                return "redirect:/login";
            }
        } else {
            return "Funcionario não encontrado!";
        }

    }

    @GetMapping("/interna")
    public String getInternaPage() {
        if (acessoFuncionario) {
            return "interna";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/buscar_patrimonio")
    public String buscarPatrimonioPorNome(@RequestParam(required = false) String nomeDoPatrimonio, Model model) {
        if (nomeDoPatrimonio != null && !nomeDoPatrimonio.isEmpty()) {
            List<Patrimonio> patrimoniosEncontrados = ptR.findByNomeDoAtivo(nomeDoPatrimonio);
            model.addAttribute("patrimoniosEncontrados", patrimoniosEncontrados);
        }
        return "interna/buscarPatrimonio";
    }

    @GetMapping("/listar_patrimonios")
    public String listarPatriomonios(Model model) {
        List<Patrimonio> patrimonios = ptR.findAll();
        model.addAttribute("patrimonios", patrimonios);
        return "interna/listarPatrimonios";
    }
    
    @GetMapping("/listar_p")
    public String listarPatriomonios(Model model) {
        List<Patrimonio> patrimonios = ptR.findAll();
        model.addAttribute("patrimonios", patrimonios);
        return "interna/listarPatrimonios";
    }
}
