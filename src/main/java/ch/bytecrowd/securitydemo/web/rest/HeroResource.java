package ch.bytecrowd.securitydemo.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.bytecrowd.securitydemo.domain.Hero;
import ch.bytecrowd.securitydemo.repository.HeroReporitory;

@Controller
@RequestMapping(path = "/api/hero")
public class HeroResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(HeroResource.class);

    private final HeroReporitory reporitory;

    public HeroResource(HeroReporitory reporitory) {
        this.reporitory = reporitory;
    }

    @PostMapping
    @ResponseBody
    public Hero createHero(@Valid @RequestBody Hero hero) {
        LOG.info("POST: /api/hero - data: {}", hero);
        return reporitory.save(hero);
    }

    @GetMapping
    @ResponseBody
    public List<Hero> getAll() {
        LOG.info("GET: /api/hero");
        return reporitory.findAll();
    }
}
