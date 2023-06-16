package com.spring.jpa.chap03_pagination.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPageRepository extends JpaRepository<Student, String> { //JpaRepository는 Page기능도 제공해준다.

    //학생 조건 없이 전체 조회 페이징(기본 기능으로 제공되고있음 -> 안만들어도됨)
    //굳이 써보자면, Page<Student> findAll(Pageable pageable);


    //근데, 페이징은 항상 전체조회만 하는게 아니잖아. 조건이 달리잖아.
    //예를들어, 학생의 이름에 특정 단어가 포함된 걸 조회하고싶어, 그리고 페이징 알고리즘도 하고싶어.
    Page<Student> findByNameContaining(String name, Pageable pageable); //매개값으로는 where절에 이름이 들어가겠지. 그래서 name적었고, 페이징 요청 번호를 알아야 그 정보들을 리턴해주잖아? JPA는 int Page가 아니라 원래 기능이 있으니까, Pageable이라는 객체를 전달해달라!

    //근데 페이징을 안할 수 있지만, 데이터가 많아진다면 반드시 페이징은 필요하니까.




    //테스트클래스만들자.


}
