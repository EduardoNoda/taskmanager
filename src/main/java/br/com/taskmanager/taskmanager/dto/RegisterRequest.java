package br.com.taskmanager.taskmanager.dto;

public record RegisterRequest(String name,String password, String email, String cpf) {
}
