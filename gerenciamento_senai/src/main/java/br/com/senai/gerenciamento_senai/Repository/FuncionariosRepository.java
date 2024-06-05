package br.com.senai.gerenciamento_senai.Repository;

import org.springframework.data.repository.CrudRepository;

import br.com.senai.gerenciamento_senai.Model.Funcionarios;
import java.util.List;

public interface FuncionariosRepository extends CrudRepository<Funcionarios, Integer> {

    boolean existsByEmail(String email);

    Funcionarios findByEmail(String email);

}
