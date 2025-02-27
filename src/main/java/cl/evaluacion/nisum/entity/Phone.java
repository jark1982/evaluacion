package cl.evaluacion.nisum.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String number;
    private String citycode;
    private String contrycode;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
