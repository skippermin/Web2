package com.example.entity;

import com.example.constant.Role;
import com.example.dto.MemberFormDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    // 회원 이메일을 통해 구분해야 하기 함 데이터베이스에 들어올 수 없도록 속성을 지정.
    @Column(unique = true)
    private String email;

    private String password;

    private String address;
    // enum 사용 시 기본적으로 순서가 저장되는데 순서가 바뀔 경우 문제가 발생. 하기에 String으로 저장하여 관리
    @Enumerated(EnumType.STRING)
    private Role role;
    // Member엔티티 생성하는 메소드. Member 엔티티에 회원을 생성하는 메소드를 만들어서 관리를 한다면 코드가 변경되더라고 한 군데만 수정하게끔 만듬.
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        //BCryptPasswordEncoder bean을 파라미터로 넘겨서 비밀번호를 암호화 합니다.
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        // 맴버 엔티티 생성 시 user role로 생성하던 권한을 admin role로 생성하도록 수정하였음.
        member.setRole(Role.ADMIN);
        return member;
    }

}
