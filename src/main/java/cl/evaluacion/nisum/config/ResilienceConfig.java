package cl.evaluacion.nisum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        // Se crea usando la configuración que se define en application.yml
        return CircuitBreakerRegistry.ofDefaults();
    }
}