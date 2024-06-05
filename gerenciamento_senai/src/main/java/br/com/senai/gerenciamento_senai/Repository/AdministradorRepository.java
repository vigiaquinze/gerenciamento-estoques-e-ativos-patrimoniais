package br.com.senai.gerenciamento_senai.Repository;

import org.springframework.data.repository.CrudRepository;

import br.com.senai.gerenciamento_senai.Model.Administrador;

public interface AdministradorRepository extends CrudRepository<Administrador, Integer>{
    
}
