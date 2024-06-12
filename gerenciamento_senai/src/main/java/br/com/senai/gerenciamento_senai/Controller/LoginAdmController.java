package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

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
    public String abrirInternaAdm(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        model.addAttribute("nPatrimonios", ptR.count());
        model.addAttribute("nAmbientes", slR.count());
        model.addAttribute("nFuncionarios", fnR.count());
        model.addAttribute("nMov", mvR.count());
        model.addAttribute("mediaPatrimonioSala", ptR.calcularMediaPatrimoniosPorSala());
        return "internaAdm/internaIndex";
    }

    @GetMapping("/logout_adm")
    public String logoutAdm() {
        acessoAdm = false;
        return "redirect:/login-adm";
    }

    @GetMapping("/adm_patrimonio")
    public String listarPatriomonios(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Patrimonio> patrimonios = (List<Patrimonio>) ptR.findAll();
        model.addAttribute("patrimonios", patrimonios);
        return "internaAdm/admPatrimonios";
    }

    @GetMapping("/adm_funcionarios")
    public String listarFuncionarios(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Funcionarios> funcionarios = (List<Funcionarios>) fnR.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "internaAdm/admFuncionarios";
    }

    @GetMapping("/adm_consumo")
    public String listarConsumo(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Consumo> itens = (List<Consumo>) csR.findAll();
        model.addAttribute("itens", itens);
        return "internaAdm/admConsumo";
    }

    @GetMapping("/adm_mov")
    public String listMovimentacao(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Movimentacao> movimentacoes = (List<Movimentacao>) mvR.findAll();
        model.addAttribute("movimentacoes", movimentacoes);
        return "internaAdm/admMovimentacao";
    }

    @GetMapping("/adm_sala")
    public String listAmbientes(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Salas> ambientes = (List<Salas>) slR.findAll();
        model.addAttribute("ambientes", ambientes);
        return "internaAdm/admAmbientes";
    }

    @GetMapping("/cadastro-funcionario")
    public String abrirCadastrarFuncionario() {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
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
    public String abrirCadastroSala(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        model.addAttribute("funcionarios", fnR.findAll());
        return "internaAdm/cadastroAmbiente";
    }

    @PostMapping("/cadastro-sala")
    public ModelAndView admCadastrarSala(Salas sala, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("redirect:/cadastro-sala");

        if (!slR.existsByNome(sala.getNome())) {
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
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
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
    public String abrirCadastroPatrimonio(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            model.addAttribute("salas", slR.findAll());
            return "internaAdm/cadastroPatrimonio";
        }
    }

    @PostMapping("/cadastro-patrimonio")
    public ModelAndView admCadastrarPatrimonio(Patrimonio patrimonio, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("redirect:/cadastro-patrimonio");

        boolean nomeJaExiste = false;
        List<Patrimonio> allPatrimonios = (List<Patrimonio>) ptR.findAll();
        for (Patrimonio patrimonio2 : allPatrimonios) {
            if (patrimonio2.getNome_do_ativo().toLowerCase() == patrimonio.getNome_do_ativo().toLowerCase()) {
                nomeJaExiste = true;
                break;
            }
        }

        if (!nomeJaExiste) {
            ptR.save(patrimonio);
            String mensagem = "Cadastro realizado com sucesso";
            System.out.println(mensagem);
            attributes.addFlashAttribute("msg", mensagem);
        } else {
            String mensagem = "Patrimonio ja cadastrado!";
            System.out.println(mensagem);
            attributes.addFlashAttribute("msg", mensagem);
        }

        return mv;
    }

    @GetMapping("/cadastro-movimentacao")
    public String cadastrarMovimentacao(@RequestParam(required = false) String idPatrimonio,
            @RequestParam(required = false) String emailDoSolicitante, Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        if (idPatrimonio != null && !idPatrimonio.isEmpty() && emailDoSolicitante != null
                && !emailDoSolicitante.isEmpty()) {
            Optional<Funcionarios> solicitante = fnR.findByEmail(emailDoSolicitante);
            Optional<Patrimonio> patrimonio = ptR.findById(Integer.parseInt(idPatrimonio));
            try {
                if (solicitante != null && patrimonio != null) {
                    List<Salas> salas = (List<Salas>) slR.findAll();
                    model.addAttribute("salas", salas);
                    model.addAttribute("msg", "Funcionario e patriomonio encontrado!");
                    model.addAttribute("solicitante", solicitante.get());
                    model.addAttribute("patrimonio", patrimonio.get());
                } else {
                    model.addAttribute("msg", "Funcionario e/ou patriomonio não encontrado!");
                }
            } catch (Exception e) {
                System.err.println(e);
                model.addAttribute("msg", "Funcionario e/ou patriomonio não encontrado!");
            }
        }
        return "internaAdm/cadastroMovimentacao";
    }

    @PostMapping("/cadastro-movimentacao")
    public String cadastrarMovimentacao(Movimentacao movimentacao, Model model) {
        try {
            mvR.save(movimentacao);
            return "redirect:/adm_mov";
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    @GetMapping("/aprovar-movimentacao")
    public String abrirAprovarMovimentacao(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Movimentacao> movimentacoesPendentes = mvR.findAllPendentes();
        model.addAttribute("movimentacoes", movimentacoesPendentes);
        return "internaAdm/aprovaMovimentacao";
    }

    @GetMapping("/aprovar/{id}")
    public String confirmarAprovacao(@PathVariable("id") Integer id, Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            model.addAttribute("movimentacao", movimentacao.get());
            return "internaAdm/confirmaAprovacao";
        }
    }

    @GetMapping("/aprovarConfirmado/{id}")
    public String aprovarMovimentacao(@PathVariable("id") Integer id) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            if (movimentacao.get() != null) {
                movimentacao.get().setStatus("APROVADO");
                mvR.save(movimentacao.get());
            }
            return "redirect:/aprovar-movimentacao";
        }
    }

    @GetMapping("/reprovar/{id}")
    public String confirmarReprovacao(@PathVariable("id") Integer id, Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            model.addAttribute("movimentacao", movimentacao.get());
            return "internaAdm/confirmaReprovacao";
        }
    }

    @GetMapping("/reprovarConfirmado/{id}")
    public String reprovarMovimentacao(@PathVariable("id") Integer id) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            if (movimentacao.get() != null) {
                movimentacao.get().setStatus("REPROVADO");
                mvR.save(movimentacao.get());
            }
            return "redirect:/aprovar-movimentacao";
        }
    }

    @GetMapping("/editar-movimentacao")
    public String abrirEditarMovimentacao(Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        }
        List<Movimentacao> movimentacoes = (List<Movimentacao>) mvR.findAllNaoCanceladasOuRealizadas();
        model.addAttribute("movimentacoes", movimentacoes);
        return "internaAdm/editaMovimentacao";
    }

    @GetMapping("/concluir/{id}")
    public String abrirConcluirMovimentacao(@PathVariable("id") Integer id, Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            if (movimentacao != null) {
                model.addAttribute("movimentacao", movimentacao.get());
                return "internaAdm/concluirMovimentacao";
            }
            return "redirect:/editar-movimentacao";
        }
    }

    @GetMapping("/concluirConfirma/{id}")
    public String concluirMovimentacao(@PathVariable("id") Integer id) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            if (movimentacao.get() != null) {
                movimentacao.get().setStatus("REALIZADA"); // Colocando a movimentacao como concluida

                Patrimonio patrimonio = movimentacao.get().getAtivo(); // Encontrando o ativo da movimentacao
                patrimonio.setSala(movimentacao.get().getDestino()); // Trocando o ambiente do ativo
                mvR.save(movimentacao.get());
                ptR.save(patrimonio);
            }
            return "redirect:/editar-movimentacao";
        }
    }

    @GetMapping("/cancelar/{id}")
    public String abrirCancelarMovimentacao(@PathVariable("id") Integer id, Model model) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            if (movimentacao != null) {
                model.addAttribute("movimentacao", movimentacao.get());
                return "internaAdm/cancelarMovimentacao";
            }
            return "redirect:/editar-movimentacao";
        }
    }

    @GetMapping("/cancelarConfirma/{id}")
    public String cancelarMovimentacao(@PathVariable("id") Integer id) {
        if (!acessoAdm) {
            return "redirect:/login-adm";
        } else {
            Optional<Movimentacao> movimentacao = mvR.findById(id);
            if (movimentacao.get() != null) {
                movimentacao.get().setStatus("CANCELADA"); // Colocando a movimentacao como cancelada
                mvR.save(movimentacao.get());
            }
            return "redirect:/editar-movimentacao";
        }
    }

    @GetMapping("/adm-registrar-saida")
    public String abrirRegistrarSaida(Model model) {
        model.addAttribute("itens", csR.findAll());
        return "internaAdm/AdmRegistrarSaida";
    }

    @GetMapping("/editar-ptr/{id}")
    public String editarPatrimonio(@PathVariable("id") Integer id, Model model) {
        if (!acessoAdm) {
            return "redirect:/";
        } else {
            Optional<Patrimonio> patrimonio = ptR.findById(id);
            if (patrimonio != null) {
                model.addAttribute("patrimonio", patrimonio.get());
                return "internaAdm/editarPatrimoniosAdm";
            } else {
                return "redirect:/adm_patrimonio";
            }

        }
    }

    @PostMapping("/editar-ptr/{id}")
    public String enviaEditarPatriomonio(@PathVariable("id") Integer id, Patrimonio patrimonio) {
        try {
            patrimonio.setId_patrimonio(id);
            ptR.save(patrimonio);
            return "redirect:/adm_patrimonio";
        } catch (Exception e) {
            System.err.println(e);
            return "redirect:/adm_patrimonio";
        }
    }

}
