package com.application.tasks.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

//Anotaciones de Lombok
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//anotación de JPA
@Entity
@Table(name = "tareas")
public class Task {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Long id;

    @Column(name= "descripcion")
    private String description;

    @Column(name = "fechaCreacion")
    private LocalDateTime dateCreate;

    @Column(name = "vigente")
    private Boolean isCurrent;

}
