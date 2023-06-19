package com.spring.jpa.chap04_relation.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DepartmentRepository extends JpaRepository <Department, Long> {

    //우리가 메서드를 만들고 fetch join만들고 강제로 조인시키자
    @Query("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees") //JPQL은 테이블 이름이 아니라 객체이름으로 FROM절 써야함. 그걸 d라고 부르겠다. 그걸 바로 조인을 진행한다. 원래라면 조인테이블을 쓰겠지만, JOIN FETCH라고쓰고, 그 뒤에 Department 라는 엔터티가 가지고 있는 employees를 써주자. Department클래스가보면 맨 아래 employees로 선언해놨다.     private List<Employee> employees = new ArrayList<>(); 이거.
    List<Department> findAllIncludeEmployees(); //메서드이름은 자유

    //즉, join fetch 키워드로, 조인에 대상이 되는 필드명을 작성하고 조인해서 가지고온다.
    //DISTINCT붙여줘서 중복을 막아주자.


}
