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
@Table(name = "tbl_emp")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long id;


    @Column(name = "emp_name", nullable = false)
    private String name;




}
