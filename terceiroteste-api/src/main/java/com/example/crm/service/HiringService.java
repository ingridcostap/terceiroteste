package com.example.crm.service;

import org.springframework.stereotype.Service;
import com.example.crm.model.Candidato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HiringService {
    private int idCandidato;
    private final Map<Integer, Candidato> candidatos;
    private final List<Integer> candidatosAprovados;

    public HiringService() {
        this.idCandidato = 1;
        this.candidatos = new HashMap<>();
        this.candidatosAprovados = new ArrayList<>();
    }

    public int iniciarProcesso(String nome) throws Exception {
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
        return id;
    }

    public void marcarEntrevista(int codCandidato) throws Exception {
        Candidato candidato = getCandidato(codCandidato);
        candidato.setStatus("Qualificado!");
        candidatosAprovados.add(candidato.getId());
    }

    public void desqualificarCandidato(int codCandidato) throws Exception {
        Candidato candidato = getCandidato(codCandidato);
        candidatos.remove(candidato.getId());
        candidatosAprovados.remove(Integer.valueOf(candidato.getId()));
    }

    public void aprovarCandidato(int codCandidato) throws Exception {
        Candidato candidato = getCandidato(codCandidato);
        candidatosAprovados.add(candidato.getId());
    }

    public String obterStatusCandidato(int codCandidato) throws Exception {
        Candidato candidato = getCandidato(codCandidato);
        return candidato.getStatus();
    }

    public List<String> obterCandidatosAprovados() throws Exception {
        List<String> nomesAprovados = new ArrayList<>();
        for (int id : candidatosAprovados) {
            Candidato candidato = getCandidato(id);
            nomesAprovados.add(candidato.getNome());
        }
        return nomesAprovados;
    }

    private Candidato getCandidato(int codCandidato) throws Exception {
        Candidato candidato = candidatos.get(codCandidato);
        if (candidato == null) {
            throw new Exception("Candidato não encontrado.");
        }
        return candidato;
    }
}