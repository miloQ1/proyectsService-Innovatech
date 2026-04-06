package cl.innovatech.servicio_proyectos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.innovatech.servicio_proyectos.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByClientClientId(Long clientId);
}
