package com.spring.jpa.chap04_relation.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import com.spring.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@Rollback(false)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    //데이터 삽입을 위해
    @BeforeEach
    void bulkInsert() {
        Department d1 = Department.builder()
                .name("영업부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();

        departmentRepository.save(d1);
        departmentRepository.save(d2);

        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(d1)
                .build();
        Employee e2 = Employee.builder()
                .name("어피치")
                .department(d1)
                .build();
        Employee e3 = Employee.builder()
                .name("프로도")
                .department(d2)
                .build();
        Employee e4 = Employee.builder()
                .name("춘식이")
                .department(d2)
                .build();

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);

    }


    
    @Test
    @DisplayName("특정 사원의 정보 조회")
    void testFindOne() {
        //given
        long id = 2L; //2번사원을 조회해보겠다.

        
        //when
        //Optional<Employee> employee = employeeRepository.findById(id); //널방지를 위해 옵셔널을 선언하는 것이다. 그러나 옵셔널말고 다른걸로 받아도됨 바로 아래로 가자.
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사원이 없음!"));//널이 왔다면, 어떻게 조치할 것인가?(Throw는 예외일부러발생시켰었잖아)


        //then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("\n\n\n");

        //단언도 넣어보자~오랜만에
        //Assertions.assertEquals("어피치", employee.getName()); 이거를, assertEquals에 커서를 놓고 알트 엔터하면 이렇게 바뀜. 임포트스태틱으로 깔끔하게.
        assertEquals("어피치", employee.getName());

        //그럼 left Join 성공.
    }




    //반대로 디팔트먼트를 보면, 임플로이에 대한 아무 정보도 없다. 결과는 평범한 SELECT문이 나오겠지.
    @Test
    @DisplayName("부서 정보 조회")
    void testFindDept() {
        //given
        long id = 1L;
        //when -> 지금은 단방향이라 연관관계안맺어놨잖아?
        Department department = departmentRepository.findById(id).orElseThrow();

        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("\n\n\n");
    }






}