package br.com.taskmanager.taskmanager.service;

import br.com.taskmanager.taskmanager.dto.RegisterRequest;
import br.com.taskmanager.taskmanager.exception.UserAlreadyExistsException;
import br.com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.name())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if (userRepository.existsByCpf(request.cpf())) {
            throw new UserAlreadyExistsException("CPF already exists");
        }

        var user = new br.com.taskmanager.taskmanager.model.UserModel();
        user.setUsername(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setCpf(request.cpf());
        user.setRole(br.com.taskmanager.taskmanager.model.RoleModel.ROLE_USER);

        userRepository.save(user);
    }

}
