package com.application.tasks.dao;

import com.application.tasks.DataTasksMock;
import com.application.tasks.entity.Task;
import com.application.tasks.repository.TaskRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskDAOImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskDAOImpl taskDAOImpl;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");

    @Test
    void testFindAllTasks() {
        when(taskRepository.findAll()).thenReturn(DataTasksMock.listTasksMock());

        List<Task> taskResultList = taskDAOImpl.findAll();

        verify(taskRepository).findAll();
        assertEquals(4, taskResultList.size());
        assertTrue(taskResultList.contains(taskResultList.get(0)));
        assertTrue(taskResultList.contains(taskResultList.get(2)));
    }

    @Test
    @SneakyThrows
    void testSaveTask() {

        when(taskRepository.save( any( Task.class))).thenReturn( DataTasksMock.newTaskMock());
        Task taskCreated = taskDAOImpl.save(DataTasksMock.newTaskMock());

        verify(taskRepository, times(1)).save( any( Task.class ));
        assertEquals(5L, taskCreated.getId());
        assertEquals("Incorporar Swagger", taskCreated.getDescription());
        assertEquals(LocalDateTime.parse("2024/08/24T14:30:00", formatter), taskCreated.getDateCreate());
        assertEquals(true, taskCreated.getIsCurrent());
    }

    @Test
    void testDeleteByIdTask() {
        Long id = 2L;

        taskDAOImpl.deleteById( id );

        verify(taskRepository, times(1)).deleteById( anyLong());
    }

    @Test
    void testFindByIdTask() {
        Long id = 2L;

        when(taskRepository.findById(id)).thenReturn(Optional.of(DataTasksMock.findTaskMock()));

        Optional<Task> taskFound = taskDAOImpl.findById(id);

        verify(taskRepository).findById( anyLong() );
        assertEquals(2L, taskFound.get().getId());
        assertEquals("Crear proyecto", taskFound.get().getDescription());
        assertEquals(LocalDateTime.parse("2024/08/21T14:30:00", formatter), taskFound.get().getDateCreate());
        assertEquals(true, taskFound.get().getIsCurrent());
    }
}