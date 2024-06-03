package br.com.senai.gerenciamento_senai.Model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Salas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bloco;

    private int numero;

    @ManyToOne
    @JoinColumn(name = "responsavel", nullable = false)
    private Funcionarios funcionario;

}
