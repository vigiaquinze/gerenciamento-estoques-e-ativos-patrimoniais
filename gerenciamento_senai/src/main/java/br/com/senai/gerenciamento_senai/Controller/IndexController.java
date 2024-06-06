package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.senai.gerenciamento_senai.Model.Consumo;
import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Model.Salas;
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository;
import br.com.senai.gerenciamento_senai.Repository.ConsumoRepository;
import br.com.senai.gerenciamento_senai.Repository.FuncionariosRepository;
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository;
import br.com.senai.gerenciamento_senai.Repository.SalasRepository;

@Controller
public class IndexController {

    @Autowired
    private FuncionariosRepository fnR;

    @Autowired
    private PatrimonioRepository ptR;

    @Autowired
    private SalasRepository slR;

    @Autowired
    private ConsumoRepository csR;

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

    @GetMapping("/listar_salas")
    public String listarSalas(Model model) {
        List<Salas> salas = (List<Salas>) slR.findAll();
        model.addAttribute("salas", salas);
        return "interna/listarSalas";
    }

    @GetMapping("/solicitar_movimentacao")
    public String abrirSolicitarMovimentacao() {
        return "interna/solicitarMovimentacao";
    }

    @GetMapping("/listar_armazem")
    public String listarArmazem(Model model) {
        List<Consumo> armazem = (List<Consumo>) csR.findAll();
        model.addAttribute("armazem", armazem);
        return "interna/listarArmazem";
    }
    

    @GetMapping("/logout")
    public String logout() {
        acessoFuncionario = false;
        return "redirect:/login";
    }
    
    
}
