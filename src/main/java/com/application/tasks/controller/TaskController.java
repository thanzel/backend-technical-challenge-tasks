package com.application.tasks.controller;

import com.application.tasks.dto.TaskDTO;
import com.application.tasks.entity.Task;
import com.application.tasks.service.ITaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Prueba técnica desarrollo de una Api Restful para el registro de tareas
 * para optar al cargo de Desarrollador Backend.
 * Fecha: Agosto 2024
 * @author Yenny Chipamo
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/registrar-tareas")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    /**
     * Método: Listar todas las tareas*
     * @return Lista DTO con las tareas registradas en Base de Datos
     */
    @GetMapping("listar")
    public ResponseEntity<List<TaskDTO>> findByAllTasks() {

        List<TaskDTO> taskDTOList = taskService.findAll()
                .stream()
                .map(task -> TaskDTO.builder()
                        .id(task.getId())
                        .description(task.getDescription())
                        .dateCreate(task.getDateCreate())
                        .isCurrent(task.getIsCurrent())
                        .build()
                ).toList();

        return ResponseEntity.ok(taskDTOList);
    }

    /**
     * Método: Agregar tareas
     * @RequestBody Parámetro de entrada: el cuerpo de una tarea
     * @return El TaskDTO con la tarea creada
     */
    @PostMapping("/crear")
    public ResponseEntity<TaskDTO> saveTask(@RequestBody @Valid TaskDTO taskDTO) throws URISyntaxException {

        Task createdTask = taskService.save(Task.builder()
                .description(taskDTO.getDescription())
                .dateCreate(taskDTO.getDateCreate())
                .isCurrent(taskDTO.getIsCurrent())
                .build()
        );

        TaskDTO createdTaskDTO = TaskDTO.builder()
                .id((createdTask.getId()))
                .description(createdTask.getDescription())
                .dateCreate(createdTask.getDateCreate())
                .isCurrent(createdTask.getIsCurrent())
                .build();

        return ResponseEntity.created(new URI("api/registrar-tareas/crear")).body(createdTaskDTO);
    }

    /**
     * Método: Eliminar tareas
     * @PathVariable Parámetro de entrada: el id de una tarea
     * @return Mensaje de tarea Eliminada de la Base de Datos
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteByIdTask (@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);

        if (taskOptional.isPresent()) {
            taskService.deleteById(id);
            return ResponseEntity.ok("Tarea Eliminada");
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Método: Actualiza tareas
     * @PathVariable Parámetro de entrada: el id de una tarea
     * @RequestBody Parámetro de entrada: el cuerpo de una tarea
     * @return Mensaje de tarea actualizada en la Base de Datos
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskService.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setDescription(taskDTO.getDescription());
            task.setDateCreate(taskDTO.getDateCreate());
            task.setIsCurrent(taskDTO.getIsCurrent());
            taskService.save(task);
            return ResponseEntity.ok("Tarea Actualizada");
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Método Adicional: Buscar una tarea
     * @PathVariable Parámetro de entrada: el id de una tarea
     * @return Busca una tarea existente en la Base de Datos
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TaskDTO> findByIdTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);

        if (taskOptional.isPresent()) { //Se debe retornar siempre un DTO por buenas prácticas (no el DAO)

            Task task = taskOptional.get();

            TaskDTO taskDTO = TaskDTO.builder()
                    .id(task.getId())
                    .description(task.getDescription())
                    .dateCreate(task.getDateCreate())
                    .isCurrent(task.getIsCurrent())
                    .build();

            return ResponseEntity.ok(taskDTO);
        }

        return ResponseEntity.notFound().build();
    }
}
