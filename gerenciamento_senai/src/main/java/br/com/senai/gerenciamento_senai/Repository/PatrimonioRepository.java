package br.com.senai.gerenciamento_senai.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.senai.gerenciamento_senai.Model.Patrimonio;
import br.com.senai.gerenciamento_senai.Model.Salas;


public interface PatrimonioRepository extends CrudRepository<Patrimonio, Integer> {

    @Query("SELECT u FROM Patrimonio u WHERE LOWER(u.nome_do_ativo) LIKE LOWER(CONCAT('%', :nomeDoAtivo, '%'))")
    List<Patrimonio> findByNomeDoAtivo(@Param("nomeDoAtivo") String nomeDoAtivo);


    List<Patrimonio> findBySala(Salas sala);



}
