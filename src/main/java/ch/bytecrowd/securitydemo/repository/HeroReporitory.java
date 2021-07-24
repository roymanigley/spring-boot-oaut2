package ch.bytecrowd.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bytecrowd.securitydemo.domain.Hero;

@Repository
public interface HeroReporitory extends JpaRepository<Hero, Long> {
    
}
