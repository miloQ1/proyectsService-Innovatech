package cl.innovatech.servicio_proyectos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.innovatech.servicio_proyectos.model.Phase;
import cl.innovatech.servicio_proyectos.model.Project;
import cl.innovatech.servicio_proyectos.model.enums.PhaseStatus;
import cl.innovatech.servicio_proyectos.repository.PhaseRepository;
import cl.innovatech.servicio_proyectos.repository.ProjectRepository;

@Service
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ProjectRepository projectRepository;

    public PhaseService(PhaseRepository phaseRepository, ProjectRepository projectRepository) {
        this.phaseRepository = phaseRepository;
        this.projectRepository = projectRepository;
    }

    public Phase createPhase(Long projectId, Phase phase) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + projectId));
        phase.setProject(project);
        if (phase.getStatus() == null) {
            phase.setStatus(PhaseStatus.PENDING);
        }
        return phaseRepository.save(phase);
    }

    public List<Phase> getPhasesByProject(Long projectId) {
        return phaseRepository.findByProjectProjectIdOrderBySequenceOrderAsc(projectId);
    }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fase no encontrada con id: " + id));
    }

    public Phase updatePhase(Long id, Phase phase) {
        Phase existente = getPhaseById(id);
        existente.setName(phase.getName());
        existente.setSequenceOrder(phase.getSequenceOrder());
        existente.setPlannedStart(phase.getPlannedStart());
        existente.setPlannedEnd(phase.getPlannedEnd());
        existente.setStatus(phase.getStatus());
        return phaseRepository.save(existente);
    }

    public void deletePhase(Long id) {
        Phase existente = getPhaseById(id);
        existente.setStatus(PhaseStatus.CANCELLED);
        phaseRepository.save(existente);
    }
}
