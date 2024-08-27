package com.application.tasks.dao;

import com.application.tasks.entity.Task;
import com.application.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskDAOImpl implements ITaskDAO{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }
}
