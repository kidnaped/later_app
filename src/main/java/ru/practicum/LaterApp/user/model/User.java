package ru.practicum.LaterApp.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", length = 64, nullable = false, unique = true)
    private String firsName;
    @Column(name = "last_name", length = 64)
    private String lastName;
    private String email;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private UserState state;
}
