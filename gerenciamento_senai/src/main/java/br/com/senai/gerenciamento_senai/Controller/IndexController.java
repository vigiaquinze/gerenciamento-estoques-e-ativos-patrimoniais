package br.com.senai.gerenciamento_senai.Controller; // Define o pacote da classe.

import java.util.List; // Importa a classe List.

import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação Autowired.
import org.springframework.stereotype.Controller; // Importa a anotação Controller.
import org.springframework.ui.Model; // Importa a classe Model.
import org.springframework.web.bind.annotation.GetMapping; // Importa a anotação GetMapping.
import org.springframework.web.bind.annotation.PostMapping; // Importa a anotação PostMapping.
import org.springframework.web.bind.annotation.RequestParam; // Importa a anotação RequestParam.

import br.com.senai.gerenciamento_senai.Model.Consumo; // Importa a classe Consumo.
import br.com.senai.gerenciamento_senai.Model.Patrimonio; // Importa a classe Patrimonio.
import br.com.senai.gerenciamento_senai.Model.Salas; // Importa a classe Salas.
import br.com.senai.gerenciamento_senai.Repository.AdministradorRepository; // Importa a interface AdministradorRepository.
import br.com.senai.gerenciamento_senai.Repository.ConsumoRepository; // Importa a interface ConsumoRepository.
import br.com.senai.gerenciamento_senai.Repository.FuncionariosRepository; // Importa a interface FuncionariosRepository.
import br.com.senai.gerenciamento_senai.Repository.PatrimonioRepository; // Importa a interface PatrimonioRepository.
import br.com.senai.gerenciamento_senai.Repository.SalasRepository; // Importa a interface SalasRepository.

@Controller // Indica que a classe é um controlador do Spring MVC.
public class IndexController { // Define a classe IndexController.

    @Autowired // Injeta automaticamente uma instância de FuncionariosRepository.
    private FuncionariosRepository fnR;

    @Autowired // Injeta automaticamente uma instância de PatrimonioRepository.
    private PatrimonioRepository ptR;

    @Autowired // Injeta automaticamente uma instância de SalasRepository.
    private SalasRepository slR;

    @Autowired // Injeta automaticamente uma instância de ConsumoRepository.
    private ConsumoRepository csR;

    private boolean acessoFuncionario = false; // Define uma variável para verificar o acesso do funcionário.

    @GetMapping("/login") // Mapeia requisições GET para o caminho "/login".
    public String loginPage() { // Método que retorna a página de login.
        return "login"; // Retorna o nome da view de login.
    }

    @PostMapping("/login") // Mapeia requisições POST para o caminho "/login".
    public String login(@RequestParam String email, @RequestParam String senha) { // Método de login que recebe email e senha como parâmetros.
        if (fnR.existsByEmail(email)) { // Verifica se o email existe no repositório.
            try {
                if (fnR.findByEmail(email).getSenha().trim().equals(senha.trim())) { // Verifica se a senha corresponde.
                    acessoFuncionario = true; // Define o acesso do funcionário como verdadeiro.
                    return "redirect:/interna"; // Redireciona para a página interna.
                } else {
                    return "Senha Incorreta"; // Retorna mensagem de senha incorreta.
                }
            } catch (Exception e) { // Captura exceções.
                System.out.println(e); // Imprime a exceção no console.
                return "redirect:/login"; // Redireciona para a página de login.
            }
        } else {
            return "Funcionario não encontrado!"; // Retorna mensagem de funcionário não encontrado.
        }
    }

    @GetMapping("/interna") // Mapeia requisições GET para o caminho "/interna".
    public String getInternaPage() { // Método que retorna a página interna.
        if (acessoFuncionario) { // Verifica se o funcionário tem acesso.
            return "interna"; // Retorna o nome da view interna.
        } else {
            return "redirect:/login"; // Redireciona para a página de login.
        }
    }

    @GetMapping("/buscar_patrimonio") // Mapeia requisições GET para o caminho "/buscar_patrimonio".
    public String buscarPatrimonioPorNome(@RequestParam(required = false) String nomeDoPatrimonio, Model model) { // Método para buscar patrimônio por nome.
        if (nomeDoPatrimonio != null && !nomeDoPatrimonio.isEmpty()) { // Verifica se o nome do patrimônio não é nulo nem vazio.
            List<Patrimonio> patrimoniosEncontrados = ptR.findByNomeDoAtivo(nomeDoPatrimonio); // Busca patrimônios pelo nome.
            model.addAttribute("patrimoniosEncontrados", patrimoniosEncontrados); // Adiciona os patrimônios encontrados ao modelo.
        }
        return "interna/buscarPatrimonio"; // Retorna a view para buscar patrimônio.
    }

    @GetMapping("/listar_patrimonios") // Mapeia requisições GET para o caminho "/listar_patrimonios".
    public String listarPatriomonios(Model model) { // Método para listar patrimônios.
        List<Patrimonio> patrimonios = ptR.findAll(); // Busca todos os patrimônios.
        model.addAttribute("patrimonios", patrimonios); // Adiciona os patrimônios ao modelo.
        return "interna/listarPatrimonios"; // Retorna a view para listar patrimônios.
    }

    @GetMapping("/listar_salas") // Mapeia requisições GET para o caminho "/listar_salas".
    public String listarSalas(Model model) { // Método para listar salas.
        List<Salas> salas = (List<Salas>) slR.findAll(); // Busca todas as salas.
        model.addAttribute("salas", salas); // Adiciona as salas ao modelo.
        return "interna/listarSalas"; // Retorna a view para listar salas.
    }

    @GetMapping("/solicitar_movimentacao") // Mapeia requisições GET para o caminho "/solicitar_movimentacao".
    public String abrirSolicitarMovimentacao() { // Método para abrir a página de solicitar movimentação.
        return "interna/solicitarMovimentacao"; // Retorna a view para solicitar movimentação.
    }

    @GetMapping("/listar_armazem") // Mapeia requisições GET para o caminho "/listar_armazem".
    public String listarArmazem(Model model) { // Método para listar armazém.
        List<Consumo> armazem = (List<Consumo>) csR.findAll(); // Busca todos os consumos (armazém).
        model.addAttribute("armazem", armazem); // Adiciona o armazém ao modelo.
        return "interna/listarArmazem"; // Retorna a view para listar armazém.
    }
    
}
