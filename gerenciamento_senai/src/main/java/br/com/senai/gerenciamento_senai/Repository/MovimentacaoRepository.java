package br.com.senai.gerenciamento_senai.Repository;

import org.springframework.data.repository.CrudRepository;

import br.com.senai.gerenciamento_senai.Model.Movimentacao;

public interface MovimentacaoRepository extends CrudRepository<Movimentacao, Integer>{
    
}
