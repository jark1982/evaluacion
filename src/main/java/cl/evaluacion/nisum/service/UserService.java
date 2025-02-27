package cl.evaluacion.nisum.service;

import cl.evaluacion.nisum.dto.UserRequestDTO;
import cl.evaluacion.nisum.dto.UserResponseDTO;

public interface UserService {

    public UserResponseDTO register(UserRequestDTO request);
    public UserResponseDTO fallbackRegister(UserRequestDTO request, Throwable t);
}
