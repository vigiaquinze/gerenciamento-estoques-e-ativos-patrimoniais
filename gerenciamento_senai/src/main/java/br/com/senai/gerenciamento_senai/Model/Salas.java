package br.com.senai.gerenciamento_senai.Model;

import java.io.Serializable;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @OneToOne
    @JoinColumn(name = "id_responsavel", nullable = true)
    private Funcionarios funcionario;

}
