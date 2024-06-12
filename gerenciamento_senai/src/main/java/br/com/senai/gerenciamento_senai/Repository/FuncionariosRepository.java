package br.com.senai.gerenciamento_senai.Repository;

import org.springframework.data.repository.CrudRepository;

import br.com.senai.gerenciamento_senai.Model.Funcionarios;
import java.util.List;
import java.util.Optional;

public interface FuncionariosRepository extends CrudRepository<Funcionarios, Integer> {

    boolean existsByEmail(String email);

    Optional<Funcionarios> findByEmail(String email);

}
