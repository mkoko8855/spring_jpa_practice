package com.spring.jpa.chap01_basic.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_product") //테이블이 Product인데 바꾸고싶으면 이렇게
public class Product {
    @Id //그리고 누가 PK인지 알려줘야 한다. id;가 PK라면, @Id를 달아주자.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //값을 생성해줘. 고유한 값으로! -> mysql에서 사용하는 autoincreament 이다. 이 아노테이션의 효과로 prod_id컬럼이 만들어지면 auto_increament가 자동으로 붙는다. AUTO는 알아서 jpa한테 전략을 구상해라(웬만하면 사용X).
    @Column(name = "prod_id") //만약 아래변수 id를 바꾸고싶다면, @Column붙여서 써주면된다. 클래스도 마찬가지다.
                              //테이블도 Product가 아니라 @Table(name = "tbl_product") 위에다가 이런식으로 해도된다.

    private long id;

    @Column(name = "prod_nm", nullable = false, length = 30) //이런식으로 원하는대로 바꿀 수 있다.
    private String Name;

    private int price;


    //시간도 자동으로 들어가게 하려면
    @CreationTimestamp //즉, create됐을 때, TimeStamp를 넣어주겠다.
    //그리고 시간이 들어가면 절대 바뀌지 않게 하겠다면
    @Column(updatable = false)
    private LocalDateTime createdDate;


    @UpdateTimestamp //수정한 값을 넣겠다.
    private LocalDateTime updateDate;


    @Enumerated(EnumType.STRING) //enum타입에 열거된 것들을 문자열 타입으로 바꿔준다. 즉, 아래 FOOD, FASHION, ELECTRONIC은 INT가 아닌 문자열 타입으로 된다. 즉, varchar로 바뀐다.
    private Category category; //이 category은 무슨타입일까? 아래 enum으로 선언했는데, 실행해보면 int타입이다.


    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }
}
