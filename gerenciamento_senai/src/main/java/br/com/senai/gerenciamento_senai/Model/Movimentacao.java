package br.com.senai.gerenciamento_senai.Model;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id_mov;

    @ManyToOne
    @JoinColumn(name="solicitante_id", nullable = false)
    private Funcionarios solicitante;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDENTE'")
    String status;

    String data_movimentacao;

    String descricao;

    @ManyToOne
    @JoinColumn(name="id_origem", nullable = false, referencedColumnName = "id")
    private Salas origem;

    @ManyToOne
    @JoinColumn(name="id_destino", nullable = false, referencedColumnName = "id")
    private Salas destino;

    @ManyToOne
    @JoinColumn(name = "id_ativo", nullable = false, referencedColumnName = "id_patrimonio")
    private Patrimonio ativo;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = "PENDENTE";
        }
    }
}
