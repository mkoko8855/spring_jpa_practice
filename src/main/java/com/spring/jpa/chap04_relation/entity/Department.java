package com.spring.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude={"employees"}) //Employee클래스에서 해석.
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


    //양방향으로, department도 employee 정보를 가지게 된다
    @OneToMany(mappedBy = "department") //읽기전용으로만 사용하기 때문에, 조인컬럼 주지않는다. 양방향 매핑에서는 상대방 엔터티의 갱신에 관여할 수 없다. 단순히 일긱전용 (조회) 용으로만 사용해야 한다. mappedBy에는 상대방 엔터티의 조인이 되는 필드명을 작성한다. (0619메모).    //employee라는 조인 테이블이 생성될 때마다 department에 매핑이 된다는 뜻이다. 그리고 초기화까지 해줘야한다(new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();








}
