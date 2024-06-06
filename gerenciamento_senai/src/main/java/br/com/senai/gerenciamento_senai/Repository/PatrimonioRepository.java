package br.com.senai.gerenciamento_senai.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.senai.gerenciamento_senai.Model.Patrimonio;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Integer> {

    @Query("SELECT p FROM Patrimonio p WHERE LOWER(p.nome_do_ativo) LIKE LOWER(CONCAT('%', :nomeDoAtivo, '%'))")
    List<Patrimonio> findByNomeDoAtivo(@Param("nomeDoAtivo") String nomeDoAtivo);

}
