package com.example.projeto.nivelDeAcesso;

import lombok.Getter;

@Getter
public enum UserRole {

    Administrador("Administrador"),
    Funcionario("Funcionario"),
    Cliente("Cliente");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
