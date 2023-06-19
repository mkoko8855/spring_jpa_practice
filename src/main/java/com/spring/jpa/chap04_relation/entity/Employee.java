package com.spring.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"department"}) //jpa 연관관계 매핑에서, 연관관계 데이터는 ToString에서 제외해야 한다. 즉, 맨 아래 depratment 변수는 ToString에서 제외해야 한다. @ToString에 속성을 지정한다. 괄호열고 exclude(제외)를 쓰자. 제외하고 싶은 필드를 쓰면 된다. 연관관계니 department를 제외시켜주자. depratment 클래스로 가서도 적어주자.
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder //참고로 빌더는 Constructor이 필요해~
@Entity
@Table(name = "tbl_emp")
public class Employee { //나 라는 기준으로, 임플로이는 N이기 때문에 @ManyToOne이다.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long id;


    @Column(name = "emp_name", nullable = false)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY) //EAGER는 항상 무조건 조인을 수행한다. 이것은 디폴트값으로 되어 있는데, employee조회하는데 department도 조인되서 조회된다. 필요한 경우에만 조인을 수행하는 LAZY로 설정해주면된다.
    @JoinColumn(name = "dept_id") //조인을 할 떄 사용하게 될 컬럼명을 적자. 조인컬럼줘서 조인할꺼라는걸 일컫음
    private Department department; //사원 정보 하나를 끌고와도, 그 사람의 매니저 아이디 라던가 로케이션 라던가 다 취득할 수 있잖아. 테이블은 이렇게 안되지만, JPA가 이렇게 써줄 수 라도 있다.
                             //디팔트먼트 클래스에서는 임플로이가 궁금하지 않으면 안적어도됨. 단방향 연관관계라고 한다.

    //만약, 임플로이는 부서정보를 얻기로했어. 내가 N이고, 디팔트먼트가 1이야. 다대1 관계니까,
    //그럴때 아노테이션은 @ManyToOne으로 적는다. 그리고 다대1니까, 조회를 할 때에는 name은 컬럼명인 dept_id로. 이렇게 단방향 연관관계로 설정했다.



    //2번째 방법을 위해 세터메서드뽑자
    public void setDepartment(Department department){
        this.department = department;

        //지금 전달된 디팔트먼트에서 사원을 얻어내고, add를 해주자. 지금 이 사원을.
        department.getEmployees().add(this);
        //즉, setDepartment를 부르기만 해도 디팔트먼트가 세팅이 되는데, 부서쪽에다가 List를 얻어서 add를 해주겠다. 지금 이 stter메서드를.
    }


}
