package com.example.chattingroom2.entity;

import com.example.chattingroom2.type.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "socialId")
    private String socialId;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, unique = true, name = "email")
    private String email;


    @Column(nullable = false, name = "provider")
    @Enumerated(EnumType.STRING)
    private Provider provider;
}
