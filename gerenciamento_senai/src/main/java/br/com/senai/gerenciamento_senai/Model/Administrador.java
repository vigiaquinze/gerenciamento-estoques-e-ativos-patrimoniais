package br.com.senai.gerenciamento_senai.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Administrador {
    @Id
    int id_adm;

    String cpf;

    String usuario;

    String senha;
}
