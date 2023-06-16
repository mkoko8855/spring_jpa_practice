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


    @ManyToOne
    @JoinColumn(name = "dept_id") //조인을 할 떄 사용하게 될 컬럼명을 적자.
    private Department department; //사원 정보 하나를 끌고와도, 그 사람의 매니저 아이디 라던가 로케이션 라던가 다 취득할 수 있잖아. 테이블은 이렇게 안되지만, JPA가 이렇게 써줄 수 라도 있다.
                             //디팔트먼트 클래스에서는 임플로이가 궁금하지 않으면 안적어도됨. 단방향 연관관계라고 한다.

    //만약, 임플로이는 부서정보를 얻기로했어. 내가 N이고, 디팔트먼트가 1이야. 다대1 관계니까,
    //그럴때 아노테이션은 @ManyToOne으로 적는다. 그리고 다대1니까, 조회를 할 때에는 name은 컬럼명인 dept_id로. 이렇게 단방향 연관관계로 설정했다.



}
