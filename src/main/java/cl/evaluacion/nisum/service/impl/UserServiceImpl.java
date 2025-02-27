package cl.evaluacion.nisum.service.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import cl.evaluacion.nisum.dto.UserRequestDTO;
import cl.evaluacion.nisum.dto.UserResponseDTO;
import cl.evaluacion.nisum.entity.Phone;
import cl.evaluacion.nisum.entity.User;
import cl.evaluacion.nisum.repository.UserRepository;
import cl.evaluacion.nisum.service.UserService;
import cl.evaluacion.nisum.util.JwtUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@Value("${regex.password:^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$}")
	private String passwordRegex;

	@Value("${regex.email:^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$}")
	private String emailRegex;

	@CircuitBreaker(name = "userService", fallbackMethod = "fallbackRegister")
	@Retry(name = "userService")
	public UserResponseDTO register(UserRequestDTO request) {
		UserResponseDTO response = new UserResponseDTO();

		// Validar email y contraseña con regex (se pueden agregar validaciones
		// adicionales)
		if (!request.getEmail().matches(emailRegex)) {
			throw new IllegalArgumentException("El correo no cumple el formato correcto");
		}
		if (!request.getPassword().matches(passwordRegex)) {
			throw new IllegalArgumentException("La contraseña no cumple el formato requerido");
		}
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new IllegalArgumentException("El correo ya registrado");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword()); // En un escenario real, aplicar encriptación
		user.setCreated(LocalDateTime.now());
		user.setModified(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		user.setIsActive(true);

		// Convertir teléfonos
		if (request.getPhones() != null) {
			user.setPhones(request.getPhones().stream().map(phoneDTO -> {
				Phone phone = new Phone();
				phone.setNumber(phoneDTO.getNumber());
				phone.setCitycode(phoneDTO.getCitycode());
				phone.setContrycode(phoneDTO.getContrycode());
				phone.setUser(user);
				return phone;
			}).collect(Collectors.toList()));
		}

		// Generar token JWT
		String token = jwtUtil.generateToken(request.getEmail());
		user.setToken(token);

		User saved = userRepository.save(user);

		response.setId(saved.getId());
		response.setCreated(saved.getCreated());
		response.setModified(saved.getModified());
		response.setLastLogin(saved.getLastLogin());
		response.setToken(saved.getToken());
		response.setIsActive(saved.getIsActive());

		return response;
	}

	// Método de fallback para Resilience4j
	public UserResponseDTO fallbackRegister(UserRequestDTO request, Throwable t) {
		throw new RuntimeException(t.getMessage());
	}
}
