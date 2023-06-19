package com.spring.jpa.chap05_practice.api;


import com.spring.jpa.chap05_practice.dto.PageDTO;
import com.spring.jpa.chap05_practice.dto.PostListResponseDTO;
import com.spring.jpa.chap05_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //레스트방식만 사용할꺼다. 리액트니까. 서버는 view페이지를 만들어주지않는다. 화면단에서 다 할것! 스부는 JSP 라이브러리받고 뷰리졸버도 받아야 레가시처럼쓸 수 있음.
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts") //컨트롤러는 요청을 받아야하니 저렇게 적어주자.
public class PostApiController {

    //규칙을 정하고 로직을 만들자
    //리소스 : 여기서 처리해야 할 자원은, 게시물이다. (게시물은 Post라는 이름)
     /*
        약속된 url요청에 따라 일관된 동작을 처리할 것이다.
        게시물 목록 조회 : /posts   ->  get방식으로 처리할 거다. posts라는 요청이 get으로 오면 목록조회이다.
        게시물 개별 조회 : /posts/{id} -> 글번호가 get방식으로 오면, 개별조회이다.
        게시물 등록 :     /posts -> post방식으로 오면 등록요청이다.
        게시물 수정 :     /posts/{id} -> put아니면 Patch로 오면 수정.
        게시물 삭제 :     /posts/{id} 가 오는데, delete방식으로 오면 삭제.

        즉, 최대한 REST방식으로 하자.
     */



    //서비스주입받을꺼잖아
    private final PostService postService; //final 싫으면 오토와이어드 달아도됨.



    //게시글 목록 조회는 /posts만 있으니까 괄호안에는 안쓰면됨.
    @GetMapping  //지금 위에 적어논 게시글 목록 조회 (약속)을 보면 {id}같은게 없고, 맨위 리퀘스트로 공통url이 작성되어있으니 작성하지 않아도됨.
    public ResponseEntity<?> list(PageDTO pageDTO){ //리턴타입은 객체정보가 되겠지. DB에서 조회한 글들의 정보. 담아서 전송(리턴)하겠지(responseEntity). 레스폰스엔터티는 응답객체에 태워서 보낼 타입을 보내자. 근데 모르겠지? ?로 처리하면된다. ?는 리턴이 될 때, 판단이 된다. 내가 무엇을 담느냐에 따라 제네릭 타입이 결정이 된다. 편하지?
        log.info("/api/v1/posts?page={}size={}", pageDTO.getPage(), pageDTO.getSize());


        //요청을 한번 넣어보자.
        //요청과 응답을 받아주는 프로그램을 하나 받아주자.
        //프로그램 이름은 구글에 포스트맨 검색하자.
        //메모장에 나머지적음. (생략).

        
        //아무튼
        //엔터티를 바로 리턴하면 안됨. 데이터 전송을 위한 객체를 따로 설정해주자.
        //글 들을 조회할 때, 화면단으로 리턴을 해줄 때, 이 PostListResponseDTO는 JSON으로 변환된다.
        //어떠한 형태의 JSON형태인지 약속을 해줘야 저 DTO객체를 어떻게 디자인 할지 알 수 있다. (백도, 프론트도!)
        //PostListReponseDTO 클래스를 만들자.

        PostListResponseDTO dto = postService.getPosts(pageDTO);





        return ResponseEntity.ok()  //200줄거고 바디에다가는 PostListResponseDTO를 전달할거다. 그럼 잭슨은 응답객체 BODY에 세팅하고 이걸 클라이언트단이 받아서 화면에 표현한다!
                             .body(dto);
    }





}
