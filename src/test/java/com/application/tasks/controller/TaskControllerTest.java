package com.application.tasks.controller;

import com.application.tasks.DataTasksMock;
import com.application.tasks.entity.Task;
import com.application.tasks.service.TaskServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskServiceImpl taskService;

    @Test
    @SneakyThrows
    void testFindByAllTasks() {

        when(taskService.findAll()).thenReturn(DataTasksMock.listTasksMock());

        mockMvc.perform(get("/api/registrar-tareas/listar"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Crear base de datos"))
                .andExpect(jsonPath("$[0].dateCreate").value("2024-08-20T14:30:00"))
                .andExpect(jsonPath("$[0].isCurrent").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Crear proyecto"))
                .andExpect(jsonPath("$[1].dateCreate").value("2024-08-21T14:30:00"))
                .andExpect(jsonPath("$[1].isCurrent").value(true));
    }

    @Test
    @SneakyThrows
    void testCreateTaskReturnOk() {

        String bodyJsonCreate = "{"
                + "\"description\":\"Incorporar Swagger\","
                + "\"dateCreate\":\"2024-08-24T14:30:00\","
                + "\"isCurrent\":true"
                + "}";

        String createdTaskDTOJson = "{"
                + "\"id\":5,"
                + "\"description\":\"Incorporar Swagger\","
                + "\"dateCreate\":\"2024-08-24T14:30:00\","
                + "\"isCurrent\":true"
                + "}";

        Task createdTask = DataTasksMock.newTaskMock();

        when(taskService.save( any( Task.class ))).thenReturn( createdTask );

        mockMvc.perform(post("/api/registrar-tareas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJsonCreate))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "api/registrar-tareas/crear"))
                .andExpect(content().json(createdTaskDTOJson));
    }

    @Test
    @SneakyThrows
    void testCreateTaskReturnIsNotOk() {

        StringBuilder bodyJsonCreate = new StringBuilder();
        StringBuilder errorJson = new StringBuilder();

        bodyJsonCreate.append("{")
                .append("\"description\":\"\",")
                .append("\"dateCreate\":\"2024-08-24T14:30:00\",")
                .append("\"isCurrent\":true")
                .append("}");

        errorJson.append("{")
                .append("\"description\":\"La descripción es requerida\"")
                .append("}");

        mockMvc.perform(post("/api/registrar-tareas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJsonCreate.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(errorJson.toString()));
    }

    @Test
    @SneakyThrows
    void testDeleteByIdTaskIsOk() {
        Long id = 1L;

        doNothing().when(taskService).deleteById( anyLong() );

        Task taskExist = DataTasksMock.findTaskMock();
        when(taskService.findById( anyLong())).thenReturn(Optional.of(taskExist));
        mockMvc.perform(delete("/api/registrar-tareas/eliminar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Tarea Eliminada"));

        verify(taskService).deleteById(id);

    }

    @Test
    @SneakyThrows
    void testDeleteByIdTaskIsNotOk() {
        Long id = 1L;

        when(taskService.findById( anyLong())).thenReturn( Optional.empty());
        doNothing().when(taskService).deleteById( anyLong() );

        mockMvc.perform(delete("/api/registrar-tareas/eliminar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @SneakyThrows
    void testUpdateTaskIsOk() {
        Long id = 2L;

        StringBuilder bodyJsonUpdate = new StringBuilder();

        bodyJsonUpdate.append("{")
                .append("\"description\":\"Crear proyecto en Spring Boot\",")
                .append("\"dateCreate\":\"2024-08-20T14:30:00\",")
                .append("\"isCurrent\":false")
                .append("}");

        Task taskExist = DataTasksMock.findTaskMock();

        when(taskService.findById( anyLong())).thenReturn(Optional.of(taskExist));

        mockMvc.perform(put("/api/registrar-tareas/actualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJsonUpdate.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Tarea Actualizada"));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskService, times(1)).save(taskCaptor.capture());

        Task capturedTask = taskCaptor.getValue();
        assertEquals("Crear proyecto en Spring Boot", capturedTask.getDescription());
        assertEquals(LocalDateTime.of(2024, 8, 20, 14, 30, 0), capturedTask.getDateCreate());
        assertEquals(false, capturedTask.getIsCurrent());
    }

    @Test
    @SneakyThrows
    void testUpdateTaskIsNotOk() {
        Long id = 2L;

        StringBuilder bodyJsonUpdate = new StringBuilder();

        bodyJsonUpdate.append("{")
                .append("\"description\":\"Crear proyecto en Spring Boot\",")
                .append("\"dateCreate\":\"2024-08-20T12:10:00\",")
                .append("\"isCurrent\":false")
                .append("}");

        when(taskService.findById( anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/registrar-tareas/actualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJsonUpdate.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testFindByIdTaskIsFound() {
        Long id = 2L;
        when(taskService.findById( anyLong() )).thenReturn(Optional.of(DataTasksMock.findTaskMock()));

        mockMvc.perform(get("/api/registrar-tareas/buscar/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.description").value("Crear proyecto"))
                .andExpect(jsonPath("$.dateCreate").value("2024-08-21T14:30:00"))
                .andExpect(jsonPath("$.isCurrent").value(true));
    }

    @Test
    @SneakyThrows
    void testFindByIdTaskIsNotFound() {
        Long id = 2L;
        when(taskService.findById( anyLong() )).thenReturn(Optional.empty() );

        mockMvc.perform(get("/api/registrar-tareas/buscar/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}