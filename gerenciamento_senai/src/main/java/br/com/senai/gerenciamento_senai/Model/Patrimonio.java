package br.com.senai.gerenciamento_senai.Model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patrimonio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_patrimonio;

    private String nome_do_ativo;

    private String data_compra;

    private String descricao;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'OTIMO'")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ambiente", nullable = false)
    private Salas sala;

    @PrePersist
    protected void prePersist() {
        // Define o status padrão como "OTIMO" se não for especificado
        if (this.status == null) {
            this.status = "OTIMO";
        }
    }
}
