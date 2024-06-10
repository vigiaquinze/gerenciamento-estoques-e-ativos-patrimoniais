package br.com.senai.gerenciamento_senai.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            return "interna/interna";
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
        List<Patrimonio> patrimonios = (List<Patrimonio>) ptR.findAll();
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
    public String abrirSolicitarMovimentacao(@RequestParam(required = false) String idPatrimonio,
            @RequestParam(required = false) String emailDoSolicitante, Model model) {
        if (idPatrimonio != null && !idPatrimonio.isEmpty() && emailDoSolicitante != null
                && !emailDoSolicitante.isEmpty()) {
            Funcionarios solicitante = fnR.findByEmail(emailDoSolicitante);
            Optional<Patrimonio> patrimonio = ptR.findById(Integer.parseInt(idPatrimonio));

            if (solicitante != null && patrimonio != null) {
                List<Salas> salas = (List<Salas>) slR.findAll();
                model.addAttribute("salas", salas);
                model.addAttribute("msg", "Funcionario e patrimonio encontrado!");
                model.addAttribute("solicitante", solicitante);
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

    @GetMapping("/logout")
    public String logout() {
        acessoFuncionario = false;
        return "redirect:/login";
    }

}
