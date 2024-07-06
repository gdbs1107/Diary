package Diary.diary.Domain.entity.member;

import Diary.diary.Domain.entity.Diary;
import Diary.diary.Domain.entity.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    private Date birthDate;

    //이걸 아이디로 쓸거임
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Delivery> delivery;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pay> pay;
}
