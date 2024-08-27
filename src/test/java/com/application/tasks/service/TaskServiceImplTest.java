package com.application.tasks.service;

import com.application.tasks.DataTasksMock;
import com.application.tasks.dao.ITaskDAO;
import com.application.tasks.entity.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private ITaskDAO iTaskDAO;
    @InjectMocks
    private TaskServiceImpl taskServiceImpl;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");

    @Test
    void testFindAllTasksIsOk() {
        //When
        when(iTaskDAO.findAll()).thenReturn(DataTasksMock.listTasksMock());
        List<Task> result = taskServiceImpl.findAll();

        //Then
        assertFalse(result.isEmpty());
        assertNotNull(result);
        verify(this.iTaskDAO, times(1)).findAll();

        assertEquals(1L, result.get(0).getId());
        assertEquals("Crear base de datos", result.get(0).getDescription());
        assertEquals(LocalDateTime.parse("2024/08/20T14:30:00", formatter), result.get(0).getDateCreate());
        assertEquals(true, result.get(0).getIsCurrent());

        assertEquals(4L, result.get(3).getId());
        assertEquals("Iniciar Pruebas", result.get(3).getDescription());
        assertEquals(LocalDateTime.parse("2024/08/23T18:40:00", formatter), result.get(3).getDateCreate());
        assertEquals(false, result.get(3).getIsCurrent());

    }

    @Test
    void testCreateIsOk() {
        Task task = DataTasksMock.newTaskMock();

        this.taskServiceImpl.save(task);

        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class); //Para capturar la data creada
        verify(this.iTaskDAO).save( taskArgumentCaptor.capture() );
        assertEquals(5L, taskArgumentCaptor.getValue().getId());
        assertEquals("Incorporar Swagger", taskArgumentCaptor.getValue().getDescription());
        assertEquals(LocalDateTime.parse("2024/08/24T14:30:00", formatter), taskArgumentCaptor.getValue().getDateCreate());
        assertEquals(true, taskArgumentCaptor.getValue().getIsCurrent());

    }

    @Test
    void testDeleteByIdIsOk() {
        Long id = 1L;

        this.taskServiceImpl.deleteById(id);

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass( Long.class);
        verify(this.iTaskDAO).deleteById( anyLong() );
        verify(this.iTaskDAO).deleteById( longArgumentCaptor.capture() );
        assertEquals(id, longArgumentCaptor.getValue());

    }

    @Test
    void testFindByIdIsOk() {
        Long id = 2L;

        when(this.iTaskDAO.findById( anyLong() )).thenReturn(Optional.of(DataTasksMock.findTaskMock()));
        Optional<Task> task = this.taskServiceImpl.findById(id);

        verify(this.iTaskDAO).findById( anyLong() );
        assertEquals(id, task.get().getId());
        assertEquals("Crear proyecto", task.get().getDescription());
        assertEquals(LocalDateTime.parse("2024/08/21T14:30:00", formatter), task.get().getDateCreate());
        assertEquals(true, task.get().getIsCurrent());
    }
}