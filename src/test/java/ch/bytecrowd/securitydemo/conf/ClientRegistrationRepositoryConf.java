package ch.bytecrowd.securitydemo.conf;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class ClientRegistrationRepositoryConf {
    
    @Bean
    ClientRegistrationRepository create() {
        return Mockito.mock(ClientRegistrationRepository.class);
    }
}
