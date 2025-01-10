package muit.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import muit.backend.domain.enums.Gender;
import muit.backend.domain.enums.Role;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;

    private String email;
    private String phone;
    private String birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void encodePassword(String password) {
        this.password = password;
    }
}
