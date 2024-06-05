package br.com.senai.gerenciamento_senai.Model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String nome_produto;

    int quantidade;

    String data_compra;

    String categoria;

    String local_armazem;

}
