package com.example.crm.repository;

import org.springframework.stereotype.Repository;
import com.example.crm.model.Candidato;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CandidatoRepository {
    private int idCandidato;
    private Map<Integer, Candidato> candidatos;

    public CandidatoRepository() {
        this.idCandidato = 1;
        this.candidatos = new HashMap<>();
    }

    public Candidato adicionarCandidato(String nome) throws Exception {
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

        return candidato;
    }

    public Candidato getCandidato(int codCandidato) throws Exception {
        Candidato candidato = candidatos.get(codCandidato);
        if (candidato == null) {
            throw new Exception("Candidato não encontrado.");
        }

        return candidato;
    }

	
	
}