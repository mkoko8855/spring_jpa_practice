package com.spring.jpa.chap05_practice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostModifyDTO {

    //클라이언트와 상의하여 수정할 때 넘어오는 정보는 title, content, 작성자는 수정하지못하도록하겠다(안보냄) 이라고 가정했을 때.


    @NotBlank
    @Size(min = 1, max = 20)
    private String title;  //변수명은 JSON데이터의 키값과 같아야한다. 얘도 상의해야해!~



    private String content;



    @NotNull //아래타입이 long이라 문자열로 안들어감. 공백이나 빈 문자열이 들어올 수 없는 타입은 낫널로 선언해야한다. 스트링은 상관없는데, long은 빈 문자열이 들어올 수가 없잖아 애초에. 공백도 그렇고.
    @Builder.Default
    private long PostNo = 0L; //id대신에 PostNo로 하자.




}
