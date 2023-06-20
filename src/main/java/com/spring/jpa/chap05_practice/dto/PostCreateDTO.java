package com.spring.jpa.chap05_practice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateDTO {

    //여긴공백이 있으면 안된다면 @NotBlack 붙여주자.
    //글자제한사이즈는 최소는 2글자, 최대는 5글자. -> 따로써줄필요가없으니편함
    @Size(min = 2, max = 5)
    @NotBlank //null, 공백, 빈문자열 다 불가!
    private String writer;

    @NotBlank //null, 공백, 빈문자열 다 불가!
    @Size(min = 1, max = 20)
    //@NotNull 낫널은 참고로 null을 허용하지 않는 것이다. 즉, 빈 문자열인 ""과 공백인 " "은 허용한다.
    //@NotEmpty 이건 참고로 null과 ""(빈 문자열)을 허용하지 않으나, 공백인 " "은 허용한다.
    private String title;


    private String content;


    private List<String> hashTags; //굳이 엔터티타입으로 받을필요없지. 문자몇개만오는데.

}

//라이브러리추가해주자
// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
// implementation 'org.springframework.boot:spring-boot-starter-validation'
// 이거를, Gradle이니, build.gradle클래스가서 붙여주자