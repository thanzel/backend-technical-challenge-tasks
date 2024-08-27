package com.application.tasks;

import com.application.tasks.entity.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public  class DataTasksMock {
    /**
     * Método para obtener una lista de tareas para asignarla a un Mock y que sirva de insumo a los tests unitarios
     * @return lista de tareas simuladas
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");
    public static List<Task> listTasksMock(){


        return List.of(
                new Task(1L, "Crear base de datos", LocalDateTime.parse("2024/08/20T14:30:00", formatter), true),
                new Task(2L, "Crear proyecto", LocalDateTime.parse("2024/08/21T14:30:00", formatter), true),
                new Task(3L, "Desarrollar API", LocalDateTime.parse("2024/08/22T14:30:00", formatter), false),
                new Task(4L, "Iniciar Pruebas", LocalDateTime.parse("2024/08/23T18:40:00", formatter), false)
        );
    }

    public static Task findTaskMock() {
        return new Task(2L, "Crear proyecto", LocalDateTime.parse("2024/08/21T14:30:00", formatter), true);
    }

    public static Task newTaskMock() {
        return new Task(5L, "Incorporar Swagger", LocalDateTime.parse("2024/08/24T14:30:00", formatter), true);
    }
}
