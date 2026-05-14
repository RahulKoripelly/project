package com.example.chess.match.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="matches")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String whitePlayer;
    private String blackPlayer;
    private String status;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
