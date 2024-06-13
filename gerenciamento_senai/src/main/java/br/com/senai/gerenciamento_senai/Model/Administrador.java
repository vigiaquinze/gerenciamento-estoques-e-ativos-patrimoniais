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
public class Administrador implements Serializable{
    @Id
    String cpf;

    String usuario;

    String senha;

    String email;
}
