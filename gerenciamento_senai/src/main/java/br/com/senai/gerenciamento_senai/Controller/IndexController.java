package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class IndexController {

    @Autowired
    private FuncionariosRepository fnR;

    @Autowired
    private PatrimonioRepository ptR;

    @Autowired
    private SalasRepository slR;

    @Autowired
    private ConsumoRepository csR;

    @Autowired
    private MovimentacaoRepository mvR;

    private boolean acessoFuncionario = false;

    // Favicn
    @GetMapping("favicon.ico")
    String favicon() {
        return "forward:/static/img/favicon.ico";
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/")
    public String login(@RequestParam String email, @RequestParam String senha) {
        if (fnR.existsByEmail(email)) {
            try {
                Optional<Funcionarios> funcionario = fnR.findByEmail(email);
                if (funcionario != null) {
                    if (funcionario.get().getSenha().trim().equals(senha.trim())) {
                        acessoFuncionario = true;
                        return "redirect:/interna";
                    } else {
                        return "Senha Incorreta";
                    }
                } else {
                    return "Funcionario não encontrado";
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
            return "interna/interna";
        } else {
            return "redirect:/";
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
        List<Patrimonio> patrimonios = (List<Patrimonio>) ptR.findAll();
        model.addAttribute("patrimonios", patrimonios);
        return "interna/listarPatrimonios";
    }

    @GetMapping("/cadastrar_patrimonio")
    public String abrirCadastrarPatrimonios(Model model) {
        if (!acessoFuncionario) {
            return "redirect:/";
        } else {
            model.addAttribute("salas", slR.findAll());
            return "interna/cadastrarPatrimonio";
        }
    }

    @PostMapping("/cadastrar_patrimonio")
    public ModelAndView cadastrarPatrimonio(Patrimonio patrimonio, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("redirect:/cadastrar_patrimonio");

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

    @GetMapping("/listar_salas")
    public String listarSalas(Model model) {
        List<Salas> salas = (List<Salas>) slR.findAll();
        model.addAttribute("salas", salas);
        return "interna/listarSalas";
    }

    @GetMapping("/solicitar_movimentacao")
    public String abrirSolicitarMovimentacao(@RequestParam(required = false) String idPatrimonio,
            @RequestParam(required = false) String emailDoSolicitante, Model model) {
        if (idPatrimonio != null && !idPatrimonio.isEmpty() && emailDoSolicitante != null
                && !emailDoSolicitante.isEmpty()) {
            Optional<Funcionarios> solicitante = fnR.findByEmail(emailDoSolicitante);
            Optional<Patrimonio> patrimonio = ptR.findById(Integer.parseInt(idPatrimonio));

            if (solicitante != null && patrimonio != null) {
                List<Salas> salas = (List<Salas>) slR.findAll();
                model.addAttribute("salas", salas);
                model.addAttribute("msg", "Funcionario e patrimonio encontrado!");
                model.addAttribute("solicitante", solicitante.get());
                model.addAttribute("patrimonio", patrimonio.get());
            } else {
                model.addAttribute("msg", "Funcionario e/ou patrimonio não encontrado!");
            }

        }
        return "interna/solicitarMovimentacao";
    }

    @PostMapping("/enviar-solicitacao")
    public String cadastrarMovimentacao(Movimentacao movimentacao, Model model) {
        try {
            System.err.println(movimentacao.getSolicitante().getNome());
            mvR.save(movimentacao);
            model.addAttribute("msgCad", "Movimentação solicitada com sucesso!");
            return "redirect:/solicitar_movimentacao";
        } catch (Exception e) {
            System.err.println(e);
            model.addAttribute("msgCad", "Erro ao solicitar movimentacao!");
            return "redirect:/solicitar_movimentacao"; // Aqui você pode retornar uma página de erro ou outra ação
                                                       // apropriada
        }
    }

    @GetMapping("/listar_armazem")
    public String listarArmazem(Model model) {
        List<Consumo> armazem = (List<Consumo>) csR.findAll();
        model.addAttribute("armazem", armazem);
        return "interna/listarArmazem";
    }

    @GetMapping("/registrar-saida")
    public String exibirFormularioSaida(Model model) {
        model.addAttribute("itens", csR.findAll());
        return "interna/registrarSaida";
    }

    @PostMapping("/registrar-saida")
    public String registrarSaida(@RequestParam Integer item, @RequestParam int quantidade) {
        Optional<Consumo> produto = csR.findById(item);
        if (produto.isPresent()) {
            Consumo novoProduto = produto.get();
            int novaQuantidade = novoProduto.getQuantidade() - quantidade;
            if (novaQuantidade >= 0) {
                novoProduto.setQuantidade(novaQuantidade);
                csR.save(novoProduto);
            } else {
                throw new RuntimeException("Quantidade insuficiente no estoque");
            }
        } else {
            throw new RuntimeException("Item não encontrado no estoque");
        }
        return "redirect:/listar_armazem"; // Redireciona de volta para a página de registro de saída
    }

    @GetMapping("/verificarPatrimonios/{id}")
    public String verificarPatrimonios(@PathVariable("id") Integer id, Model model) {
        Optional<Salas> salaSelecionada = slR.findById(id);
        if (salaSelecionada != null) {
            List<Patrimonio> patrimonios = ptR.findBySala(salaSelecionada.get());
            model.addAttribute("patrimonios", patrimonios);
            model.addAttribute("sala", salaSelecionada.get());
            return "interna/verPatrimoniosSala";
        } else {
            return "redirect:/listar_salas";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarPatrimonio(@PathVariable("id") Integer id, Model model) {
        if (!acessoFuncionario) {
            return "redirect:/";
        } else {
            Optional<Patrimonio> patrimonio = ptR.findById(id);
            if (patrimonio != null) {
                model.addAttribute("patrimonio", patrimonio.get());
                return "interna/editaPatrimonio";
            } else {
                return "redirect:/listar_patrimonios";
            }

        }
    }

    @PostMapping("/editar/{id}")
    public String enviaEditarPatriomonio(@PathVariable("id") Integer id, Patrimonio patrimonio) {
        try {
            patrimonio.setId_patrimonio(id);
            ptR.save(patrimonio);
            return "redirect:/listar_patrimonios";
        } catch (Exception e) {
            System.err.println(e);
            return "redirect:/listar_patrimonios";
        }
    }

    @GetMapping("/listar_movimentacoes")
    public String getMethodName(Model model) {
        model.addAttribute("movimentacoes", mvR.findAll());
        return "interna/listarMovimentacoes";
    }

    @GetMapping("/logout")
    public String logout() {
        acessoFuncionario = false;
        return "redirect:/";
    }

}
