package Diary.diary.Domain.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private Date birthDate;
    private String password;

    // Constructor
    public MemberDto() {
    }

    @Builder
    public MemberDto(Long id, String name, String email, Date birthDate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
    }
}
