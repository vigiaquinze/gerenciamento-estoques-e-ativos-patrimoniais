package br.com.senai.gerenciamento_senai.Model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Funcionarios implements Serializable {
    @Id
    int re;

    String email;

    String setor;

    String nome;

    String cargo;

    String senha;

}
