package br.com.senai.gerenciamento_senai;

// Importa classes necessárias do Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Anotação que marca a classe como uma aplicação Spring Boot
@SpringBootApplication
public class GerenciamentoSenaiApplication {

    // Método principal que inicia a aplicação Spring Boot
    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoSenaiApplication.class, args);
    }
}
