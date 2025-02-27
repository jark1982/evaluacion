package cl.evaluacion.nisum.controller;

import cl.evaluacion.nisum.dto.ErrorResponse;
import cl.evaluacion.nisum.dto.UserRequestDTO;
import cl.evaluacion.nisum.dto.UserResponseDTO;
import cl.evaluacion.nisum.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Registro de usuario")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO request) {
        try {
            UserResponseDTO response = userService.register(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
}
