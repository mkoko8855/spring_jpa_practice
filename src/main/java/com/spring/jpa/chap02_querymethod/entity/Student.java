package com.spring.jpa.chap02_querymethod.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;



@Setter //실무적 측면에서 setter는 신중하게 만들 것. 세터가 있으면 객체 안에 있는 값을 변경할 수가 있다. 그 말은 곧, 고유한 값을 지키기 위한 id의 값도 변경될 수 있다.
@Getter
@ToString
@EqualsAndHashCode(of = "id") //즉, id같으면 같은 객체로 인식시키겠다. of = {"name", "id"} 이러면 저 2개가 같아야 같은 객체로 인식시킨다. 뭐 이런거다!
@NoArgsConstructor
@AllArgsConstructor
@Builder //객체 생성할 때, 대신 객체 생성해준다. 내가 던지는 값으로만 초기화해서 객체만들어준다. Student.biluder().name(어쩌고).major(저쩌고) 이런게 가능함.
@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @Column(name = "stu_id")
    @GeneratedValue(generator = "uid") //PK값을 생성할껀데 생성해주는얘는 아래 uid를 지목하고있다.
    @GenericGenerator(strategy = "uuid", name = "uid") //이 메서드는 이용해서 GeneratedValue를 생성하는 메서드도 추가해줘야 한다. uid는 내가 마음대로 지었다.
    private String id; //변수이름이 stuId면 위에 @Column안써도되는데 변수이름이 그냥 id면 위에 Column을 써줘야함. uuid를 보여주기위해 String으로 선언했다.
    //이제 변수명 id에는 uid값이 들어간다!


    @Column(name = "stu_name", nullable = false) //널불가
    private String name;

    private String city;

    private String major;
}
