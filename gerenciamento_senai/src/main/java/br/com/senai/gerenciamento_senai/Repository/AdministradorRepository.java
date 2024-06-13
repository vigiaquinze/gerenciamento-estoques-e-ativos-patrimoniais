package br.com.senai.gerenciamento_senai.Repository;

import org.springframework.data.repository.CrudRepository;

import br.com.senai.gerenciamento_senai.Model.Administrador;
import java.util.List;

public interface AdministradorRepository extends CrudRepository<Administrador, String> {

    Administrador findByEmail(String email);

    boolean existsByEmail(String email);
}
