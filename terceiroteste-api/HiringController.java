package com.example.crm.controller;



import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.model.Candidato;

@RestController
@RequestMapping("/api/v1/hiring")

public class HiringController {
   

    @PostMapping("/start")
    
    public ResponseEntity<Integer> iniciarProcesso(@RequestBody Map<String, String> requestBody)
    {	
        String nome = requestBody.get("nome");
        try {
            if (nome == null || nome.isEmpty()) {
                throw new Exception("Nome Inválido.");
            }

            for (Candidato candidato : candidatos.values()) {
                if (candidato.getNome().equalsIgnoreCase(nome)) {
                    throw new Exception("Candidato já participa do processo.");
                }
            }

            int id = idCandidato++;
            Candidato candidato = new Candidato(id, nome, "Recebido!");
            candidatos.put(id, candidato);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    	}
    

    @PostMapping("/schedule")
    public ResponseEntity<Void> marcarEntrevista(@RequestBody Map<String, Integer> requestBody) {
        int codCandidato = requestBody.get("codCandidato");
        try {
            Candidato candidato = getCandidato(codCandidato);
            candidato.setStatus("Qualificado!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/disqualify")
    public ResponseEntity<Void> desqualificarCandidato(@RequestBody Map<String, Integer> requestBody) {
        int codCandidato = requestBody.get("codCandidato");
        try {
            Candidato candidato = getCandidato(codCandidato);
            candidatos.remove(codCandidato);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<Void> aprovarCandidato(@RequestBody Map<String, Integer> requestBody) {
        int codCandidato = requestBody.get("codCandidato");
        try {
            Candidato candidato = getCandidato(codCandidato);
            candidato.setStatus("Aprovado");
            candidatosAprovados.add(codCandidato);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/status/candidate/{codCandidato}")
    public ResponseEntity<String> verificarStatusCandidato(@PathVariable int codCandidato) {
        try {
            Candidato candidato = getCandidato(codCandidato);
            return ResponseEntity.ok(candidato.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/approved")
    public ResponseEntity<List<String>> obterAprovados() {
       
            List<String> nomesAprovados = new ArrayList<>();
            for (int id : candidatosAprovados) {
                Candidato candidato = getCandidato(id);
                nomesAprovados.add(candidato.getNome());
           
            return ResponseEntity.ok(nomesAprovados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
