package com.application.tasks.service;

import com.application.tasks.entity.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService { //Esto crea una arquitectura desacoplada, de etsa forma poder escalar óptimamente

    List<Task> findAll();  //Listar todas las tareas

    Task save(Task task);  //Crear tarea

    void deleteById(Long id); //Elimimar tarea

    Optional<Task> findById(Long id); //Listar una tarea específica
}
