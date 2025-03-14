package com.example.projeto.model;

import com.example.projeto.nivelDeAcesso.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "comanda")
    private int comand;

    @Column(name = "nome")
    private String name;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "endereço")
    private String address;

    @Column(name = "Nivel de acesso")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

