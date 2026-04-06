package cl.innovatech.servicio_proyectos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.innovatech.servicio_proyectos.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectProjectId(Long projectId);

    List<Task> findByPhasePhaseId(Long phaseId);
}
