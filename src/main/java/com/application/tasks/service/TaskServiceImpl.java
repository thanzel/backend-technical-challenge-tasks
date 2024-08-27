package com.application.tasks.service;

import com.application.tasks.dao.ITaskDAO;
import com.application.tasks.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService{

    @Autowired
    private ITaskDAO taskDAO;

    /**
     * Lista todas las tareas registradas
     *
     * @return La lista de tareas registradas en BD
     */
    @Override
    public List<Task> findAll() {
        return taskDAO.findAll();
    }

    /**
     * Registra una nueva tarea en la base de datos
     * @param task
     * @return La tarea registrada
     */
    @Override
    public Task save(Task task) {
        return taskDAO.save(task);
    }

    /**
     * Elimina una tarea de la base de datos
     * @param id
     */

    @Override
    public void deleteById(Long id) {
        taskDAO.deleteById(id);
    }

    /**
     * Busca una tarea según el id
     * @param id
     * @return Opcional, la tarea encontrada
     */
    @Override
    public Optional<Task> findById(Long id) {
        return taskDAO.findById(id);
    }
}
