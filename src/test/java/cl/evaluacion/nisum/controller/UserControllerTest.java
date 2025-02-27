package cl.evaluacion.nisum.controller;

import cl.evaluacion.nisum.dto.PhoneDTO;
import cl.evaluacion.nisum.dto.UserRequestDTO;
import cl.evaluacion.nisum.dto.UserResponseDTO;
import cl.evaluacion.nisum.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)

public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @WithMockUser  // Simula un usuario autenticado con roles por defecto (ROLE_USER)
    public void testRegisterUserSuccess() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Juan Rodriguez");
        request.setEmail("juan@rodriguez.org");
        request.setPassword("Password1");
        PhoneDTO phone = new PhoneDTO();
        phone.setNumber("1234567");
        phone.setCitycode("1");
        phone.setContrycode("57");
        request.setPhones(Collections.singletonList(phone));
        
        UserResponseDTO response = new UserResponseDTO();
        response.setId(UUID.randomUUID());
        response.setCreated(LocalDateTime.now());
        response.setModified(LocalDateTime.now());
        response.setLastLogin(LocalDateTime.now());
        response.setToken("jwt-token");
        response.setIsActive(true);
        
        Mockito.when(userService.register(Mockito.any())).thenReturn(response);
        
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.token").value("jwt-token"));
    }
}
