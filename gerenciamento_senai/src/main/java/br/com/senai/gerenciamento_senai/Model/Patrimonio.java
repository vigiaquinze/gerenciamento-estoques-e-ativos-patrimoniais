package br.com.senai.gerenciamento_senai.Model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Temporal(TemporalType.DATE)
    private Date data_compra;

    private String descricao;
    
    private String status;

    @ManyToOne
    @JoinColumn(name = "ambiente", nullable = false)
    private Salas sala;
}
