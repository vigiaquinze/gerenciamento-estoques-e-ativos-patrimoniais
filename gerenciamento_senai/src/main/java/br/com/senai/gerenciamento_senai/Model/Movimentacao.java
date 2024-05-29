package br.com.senai.gerenciamento_senai.Model;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_movimentacao;

    @OneToOne
    @JoinColumn(name="solicitante_id", nullable = false)
    private Funcionarios solicitante;

    @OneToOne
    @JoinColumn(name="aprovador_id", nullable = false)
    private Funcionarios aprovador;
    
    @Nonnull
    String status;

    @Temporal(TemporalType.DATE)
    Date data_movimentacao;

    String descricao;

    @OneToOne
    @JoinColumn(name="")
    int id_origem;
    int id_destino;
}
