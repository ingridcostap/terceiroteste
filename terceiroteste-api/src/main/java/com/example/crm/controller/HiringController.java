package com.example.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.example.crm.repository.CandidatoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.service.HiringService;

@RestController
@RequestMapping("/api/v1/hiring")
public class HiringController {
    private final HiringService segundoService;

    @Autowired
    private CandidatoRepository candidatoRepository;
    
    public HiringController(HiringService segundoService) {
        this.segundoService = segundoService;
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public int iniciarProcesso(@RequestBody String nome) throws Exception {
        return segundoService.iniciarProcesso(nome);
    }

    @PostMapping("/schedule")
    public void marcarEntrevista(@RequestBody int codCandidato) throws Exception {
        segundoService.marcarEntrevista(codCandidato);
    }

    @PostMapping("/disqualify")
    public void desqualificarCandidato(@RequestBody int codCandidato) throws Exception {
        segundoService.desqualificarCandidato(codCandidato);
    }

    @PostMapping("/approve")
    public void aprovarCandidato(@RequestBody int codCandidato) throws Exception {
        segundoService.aprovarCandidato(codCandidato);
    }

    @GetMapping("/status/candidate/{id}")
    public String obterStatusCandidato(@PathVariable("id") int idCandidato) throws Exception {
        return segundoService.obterStatusCandidato(idCandidato);
    }

    @GetMapping("/approved")
    public List<String> obterCandidatosAprovados() throws Exception {
        return segundoService.obterCandidatosAprovados();
    }
}