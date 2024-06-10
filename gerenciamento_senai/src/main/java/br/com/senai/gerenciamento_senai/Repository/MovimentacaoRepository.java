package br.com.senai.gerenciamento_senai.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.senai.gerenciamento_senai.Model.Movimentacao;

public interface MovimentacaoRepository extends CrudRepository<Movimentacao, Integer> {
    @Query("select m from Movimentacao m where m.status = 'PENDENTE'")
    List<Movimentacao> findAllPendentes();
}
