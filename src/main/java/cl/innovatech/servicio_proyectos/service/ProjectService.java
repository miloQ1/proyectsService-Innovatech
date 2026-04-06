package cl.innovatech.servicio_proyectos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.innovatech.servicio_proyectos.model.Client;
import cl.innovatech.servicio_proyectos.model.Project;
import cl.innovatech.servicio_proyectos.model.enums.ProjectStatus;
import cl.innovatech.servicio_proyectos.repository.ClientRepository;
import cl.innovatech.servicio_proyectos.repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;

    public ProjectService(ProjectRepository projectRepository, ClientRepository clientRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }

    public Project createProject(Long clientId, Project project) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + clientId));
        project.setClient(client);
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.PLANNING);
        }
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByClient(Long clientId) {
        return projectRepository.findByClientClientId(clientId);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
    }

    public Project updateProject(Long id, Project project) {
        Project existente = getProjectById(id);
        existente.setCode(project.getCode());
        existente.setName(project.getName());
        existente.setDescription(project.getDescription());
        existente.setStartDate(project.getStartDate());
        existente.setEndDate(project.getEndDate());
        existente.setBudget(project.getBudget());
        existente.setStatus(project.getStatus());
        existente.setProgressPct(project.getProgressPct());
        existente.setProjectManagerId(project.getProjectManagerId());
        return projectRepository.save(existente);
    }

    public void deleteProject(Long id) {
        Project existente = getProjectById(id);
        existente.setStatus(ProjectStatus.CANCELLED);
        projectRepository.save(existente);
    }
}
