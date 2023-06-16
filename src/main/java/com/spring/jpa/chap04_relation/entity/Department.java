package com.spring.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder //참고로 빌더는 Constructor이 필요해~
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private long id;



    @Column(name = "dept_name", nullable = false)
    private String name;









}
