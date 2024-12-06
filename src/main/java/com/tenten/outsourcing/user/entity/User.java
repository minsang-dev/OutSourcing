package com.tenten.outsourcing.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenten.outsourcing.common.Role;
import com.tenten.outsourcing.common.BaseEntity;
import com.tenten.outsourcing.config.PasswordEncoder;
import com.tenten.outsourcing.user.dto.UserRequestDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String email;

    @Convert(converter = PasswordEncoder.class)
    @Column(length = 100)
    private String password;

    @Column(length = 10)
    private String name;

    @Column(length = 100)
    private String address;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Setter
    @JsonFormat(pattern = "yy:MM:dd hh:mm:ss")
    private LocalDateTime deletedAt;

    public User(UserRequestDto userRequestDto) {
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.name = userRequestDto.getName();
        this.address = userRequestDto.getAddress();
        this.role = userRequestDto.getRole();
    }

    public User(String email, String password, String name, String address, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }
}
