package com.spring.jpa.chap04_relation.repository;


import com.spring.jpa.chap04_relation.entity.Department;
import com.spring.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {

    @Autowired
    EntityManager entityManager;


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @DisplayName("특정 부서를 조회하면 해당 부서원들도 함께 조회 되어야 한다.")
    void testFindDept() {
        //given
        long id = 2L;
        //when
        Department department = departmentRepository.findById(id).orElseThrow();//문제가 발생한다면, 안에는 발생할 타입을 적으면됨(컨트롤 엔터로 지역변수로)
        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);//그러나 여기서 에러난다. @ToString이 문제다. 양 방향에서는 @ToString 주면 안된다. Employee가자.
        System.out.println("\n\n\n");
        //즉, 부서정보에 사원정보도 들어있는 것을 보기 위해 조회해보자. 됨!


        System.out.println("department.getEmployees() =" + department.getEmployees()); //이것도 뽑아보자

    }


    @Test
    @DisplayName("LAZY 로딩과 EAGER 로딩의 차이")
    void testLazyAndEager() {
        //3번 사원을 조회 하고 싶은데, 굳이 부서 정보는 필요없다.
        //given
        long id = 3L;
        //when
        Employee employee = employeeRepository.findById(id).orElseThrow(); //에러타입을 적어줘야하는제 귀찮아서 빈곳으로..

        //then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("employee = " + employee.getDepartment()); //이러면 depratment의 정보를 게으르게 끌고오겠지. employee엔터티에 LAZY붙여줬으니 JOIN은 최대한 미루겠지. JPA가 데이터를 조인필요있나없나를 판단해서 조회해주겠지.
        System.out.println("\n\n\n");
    }


    @Test
    @DisplayName("양방향 연관관계에서 연관데이터의 수정")
    void testChangeDept() {
        //3번 사원의 부서를 2번 부서에서 1번 부서로 변경하자.
        //given
        long id = 3L;
        //when
        //일단 사원을 찾아야겠지 (3번사원조회)
        Employee foundEmp = employeeRepository.findById(id).orElseThrow();

        //지금 이 사원이 부서가 2번인데, 부서를 몇번으로 변경할건지. 1번으로 바꿀꺼잖아. (1번사원조회한거임)
        Department newDept = departmentRepository.findById(1L).orElseThrow();

        //변경감지(더티세팅. 새롭게 세팅했음)
        //2번방법. 사원의 부서정보를 업데이트 하면서, 부서에 대한 정보도 같이 업데이트를 진행한다. employee.java가보면 세팅해놨음.
        foundEmp.setDepartment(newDept);

        //엔터티가 변경되었으니, save를 진행하면 update가 들어간다
        employeeRepository.save(foundEmp);


        //1번째 방법을 위한 것을 써주자. 더티체크다. 플러시와 클리어를 진행하는데, 변경을 감지한 후에 변경이 되고 난 데이터를 save한다고 얘기했었잖아 위에. 즉, 변경 감지 후 변경된 내용을 DB에 반영하는 역할을 한다.
        //entityManager.flush();
        //entityManager.clear(); 이제 주석처리하고 2번방법써보자.


        //then
        //이번엔 수정 후, 1번 부서 정보를 조회해서 모든 사원을 살펴보자.
        Department foundDept = departmentRepository.findById(1L).orElseThrow();

        //반복문 > foundDept에서 getList를 꺼내고 forEach로 돌릴 것이다.
        System.out.println("\n\n\n");
        foundDept.getEmployees().forEach(System.out::println);
        System.out.println("\n\n\n");


        //그러나 결과는 조회부터 진행하고 save가 들어간다.
        //save(update)들어가고 조회해야되는데..
        //어떻게해? -> 0619
    }


    @Test
    @DisplayName("N + 1 문제 발생 예시")
    void testNPlus1Ex() {
        //given
        //부서 다 가져와
        List<Department> departments = departmentRepository.findAll();
        //when
        //반복문돌리자
        departments.forEach(dept -> {
            System.out.println("\n\n======= 사원 리스트 =======");
            //부서가 dept변수로 하나씩 들어오잖아. 사원을 뽑자.
            List<Employee> employees = dept.getEmployees();
            System.out.println("employees = " + employees);
            System.out.println("\n\n");
        });
    }
    //then


    @Test
    @DisplayName("N + 1 문제 해결 예시")
    void testNPlus1Solution() {
        //given
        List<Department> departments = departmentRepository.findAllIncludeEmployees();
        //when
        departments.forEach(dept -> {
            System.out.println("\n\n======= 사원 리스트 =======");
            List<Employee> employees = dept.getEmployees();
            System.out.println("employees = " + employees);
            System.out.println("\n\n");
        });
    }






}