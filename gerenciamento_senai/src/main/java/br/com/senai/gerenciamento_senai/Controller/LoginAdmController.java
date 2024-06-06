package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.senai.gerenciamento_senai.Model.Consumo;
import br.com.senai.gerenciamento_senai.Model.Funcionarios;
import br.com.senai.gerenciamento_senai.Model.Movimentacao;
import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Model.Salas;
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository;
import br.com.senai.gerenciamento_senai.Repository.ConsumoRepository;
import br.com.senai.gerenciamento_senai.Repository.FuncionariosRepository;
import br.com.senai.gerenciamento_senai.Repository.MovimentacaoRepository;
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository;
import br.com.senai.gerenciamento_senai.Repository.SalasRepository;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginAdmController {

    @Autowired
    AdministradorRepository admR;

    @Autowired
    PatrimonioRepository ptR;

    @Autowired
    FuncionariosRepository fnR;

    @Autowired
    ConsumoRepository csR;

    @Autowired
    MovimentacaoRepository mvR;

    @Autowired
    SalasRepository slR;

    boolean acessoAdm = false;

    @GetMapping("/login-adm")
    public String loginPage() {
        return "loginAdm";
    }

    @PostMapping("/login-adm")
    public String login(@RequestParam String email, @RequestParam String senha) {
        if (admR.existsByEmail(email)) {
            try {
                if (admR.findByEmail(email).getSenha().trim().equals(senha.trim())) {
                    acessoAdm = true;
                    return "redirect:/interna-adm";
                } else {
                    return "Senha Incorreta";
                }
            } catch (Exception e) {
                System.out.println(e);
                return "redirect:/login-adm";
            }
        } else {
            return "Adm não encontrado!";
        }

    }

    @GetMapping("/interna-adm")
    public String abrirInternaAdm() {
        return "internaAdm/internaIndex";
    }

    @GetMapping("/logout_adm")
    public String logoutAdm() {
        acessoAdm = false;
        return "redirect:/login-adm";
    }

    @GetMapping("/adm_patrimonio")
    public String listarPatriomonios(Model model) {
        List<Patrimonio> patrimonios = ptR.findAll();
        model.addAttribute("patrimonios", patrimonios);
        return "internaAdm/admPatrimonios";
    }

    @GetMapping("/adm_funcionarios")
    public String listarFuncionarios(Model model) {
        List<Funcionarios> funcionarios = (List<Funcionarios>) fnR.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "internaAdm/admFuncionarios";
    }

    @GetMapping("/adm_consumo")
    public String listarConsumo(Model model) {
        List<Consumo> itens = (List<Consumo>) csR.findAll();
        model.addAttribute("itens", itens);
        return "internaAdm/admConsumo";
    }

    @GetMapping("/adm_mov")
    public String listMovimentacao(Model model) {
        List<Movimentacao> movimentacoes = (List<Movimentacao>) mvR.findAll();
        model.addAttribute("movimentacoes", movimentacoes);
        return "internaAdm/admMovimentacao";
    }

    @GetMapping("/adm_sala")
    public String listAmbientes(Model model) {
        List<Salas> ambientes = (List<Salas>) slR.findAll();
        model.addAttribute("ambientes", ambientes);
        return "internaAdm/admAmbientes";
    }

    @GetMapping("/cadastro-funcionario")
    public String abrirCadastrarFuncionario() {
        return "internaAdm/cadastroFuncionario";
    }

    @PostMapping("/cadastro-funcionario")
    public ModelAndView admCadastrarFuncionario(Funcionarios funcionario, RedirectAttributes attributes) {

        ModelAndView mv = new ModelAndView("redirect:/cadastro-funcionario");

        if (!fnR.existsByEmail(funcionario.getEmail()) && !fnR.existsById(funcionario.getRe())) {
            fnR.save(funcionario);
            String mensagem = "Cadastro realizado com sucesso";
            System.out.println(mensagem);
            attributes.addFlashAttribute("msg", mensagem);
        } else {
            String mensagem = "Erro! Cadastro inválido. E-mail ou RE em uso!";
            System.out.println(mensagem);
            attributes.addFlashAttribute("msg", mensagem);
        }

        return mv;
    }

    @GetMapping("/cadastro-sala")
    public String abrirCadastroSala() {
        return "internaAdm/cadastroAmbiente";
    }

    @PostMapping("/cadastro-sala")
    public ModelAndView admCadastrarSala(Salas sala, RedirectAttributes attributes) {

        ModelAndView mv = new ModelAndView("redirect:/cadastro-sala");

        if (slR.existsByNome(sala.getNome())) {
            slR.save(sala);
            String mensagem = "Cadastro realizado com sucesso";
            System.out.println(mensagem);
            attributes.addFlashAttribute("msg", mensagem);
        } else {
            String mensagem = "Erro! Cadastro inválido. Sala ja cadastrada!";
            System.out.println(mensagem);
            attributes.addFlashAttribute("msg", mensagem);
        }

        return mv;
    }

    @GetMapping("/cadastro-consumo")
    public String abrirCadastroConsumo() {
        return "internaAdm/cadastroConsumo";
    }

    @PostMapping("/cadastro-consumo")
    public ModelAndView admCadastrarConsumo(Consumo consumo, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("redirect:/cadastro-consumo");

        // if (!csR.existsByNome_produto(consumo.getNome_produto())) {
        csR.save(consumo);
        String mensagem = "Cadastro realizado com sucesso";
        System.out.println(mensagem);
        attributes.addFlashAttribute("msg", mensagem);
        // // } else {
        // String mensagem = "Erro! Cadastro inválido. Produto já cadastrado!";
        // System.out.println(mensagem);
        // attributes.addFlashAttribute("msg", mensagem);
        // }

        return mv;
    }

    @GetMapping("/cadastro-patrimonio")
    public String abrirCadastroPatrimonio() {
        return "internaAdm/cadastroPatrimonio";
    }

    @PostMapping("/cadastro-patrimonio")
    public ModelAndView admCadastrarPatrimonio(Patrimonio patrimonio, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("redirect:/cadastro-patrimonio");

        // if (!ptR.existsByNomeDoAtivo(patrimonio.getNome_do_ativo())) {
        ptR.save(patrimonio);
        String mensagem = "Cadastro realizado com sucesso";
        System.out.println(mensagem);
        attributes.addFlashAttribute("msg", mensagem);
        // } else {
        // String mensagem = "Erro! Cadastro inválido. Ativo já cadastrado!";
        // System.out.println(mensagem);
        // attributes.addFlashAttribute("msg", mensagem);
        // }

        return mv;
    }

}
