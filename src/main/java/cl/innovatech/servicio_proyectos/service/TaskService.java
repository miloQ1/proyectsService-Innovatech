package cl.innovatech.servicio_proyectos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.innovatech.servicio_proyectos.model.Phase;
import cl.innovatech.servicio_proyectos.model.Project;
import cl.innovatech.servicio_proyectos.model.Task;
import cl.innovatech.servicio_proyectos.model.enums.TaskStatus;
import cl.innovatech.servicio_proyectos.repository.PhaseRepository;
import cl.innovatech.servicio_proyectos.repository.ProjectRepository;
import cl.innovatech.servicio_proyectos.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, PhaseRepository phaseRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.phaseRepository = phaseRepository;
    }

    public Task createTask(Long projectId, Task task) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    task.setProject(project);

    if (task.getInputPhaseId() != null) {
        Phase phase = phaseRepository.findById(task.getInputPhaseId())
            .orElseThrow(() -> new RuntimeException("Fase no encontrada"));
        task.setPhase(phase);
    }

    if (task.getStatus() == null) task.setStatus(TaskStatus.TODO);

    // Generar código: usa el código del proyecto + número secuencial
    long count = taskRepository.countByProjectProjectId(projectId);
    String code = project.getCode().split("-")[0] + "-" + String.format("%03d", count + 1);
    task.setTaskCode(code);

    return taskRepository.save(task);
}

    public List<Task> getTasksByProject(Long projectId) {
    return taskRepository.findByProjectProjectIdWithPhase(projectId);
}

    public List<Task> getTasksByPhase(Long phaseId) {
        return taskRepository.findByPhasePhaseId(phaseId);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
    }

    public Task updateTask(Long id, Task task) {
        Task existente = getTaskById(id);
        existente.setTitle(task.getTitle());
        existente.setDescription(task.getDescription());
        existente.setPriority(task.getPriority());
        existente.setStatus(task.getStatus());
        existente.setAssignedResourceId(task.getAssignedResourceId());
        existente.setEstimatedHours(task.getEstimatedHours());
        existente.setActualHours(task.getActualHours());
        existente.setStartDate(task.getStartDate());
        existente.setDueDate(task.getDueDate());
        return taskRepository.save(existente);
    }

    public void deleteTask(Long id) {
        Task existente = getTaskById(id);
        existente.setStatus(TaskStatus.CANCELLED);
        taskRepository.save(existente);
    }
}
