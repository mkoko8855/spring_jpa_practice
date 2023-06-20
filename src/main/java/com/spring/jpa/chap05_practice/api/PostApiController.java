package com.spring.jpa.chap05_practice.api;


import com.spring.jpa.chap05_practice.dto.PageDTO;
import com.spring.jpa.chap05_practice.dto.PostCreateDTO;
import com.spring.jpa.chap05_practice.dto.PostDetailResponseDTO;
import com.spring.jpa.chap05_practice.dto.PostListResponseDTO;
import com.spring.jpa.chap05_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> list(PageDTO pageDTO) { //리턴타입은 객체정보가 되겠지. DB에서 조회한 글들의 정보. 담아서 전송(리턴)하겠지(responseEntity). 레스폰스엔터티는 응답객체에 태워서 보낼 타입을 보내자. 근데 모르겠지? ?로 처리하면된다. ?는 리턴이 될 때, 판단이 된다. 내가 무엇을 담느냐에 따라 제네릭 타입이 결정이 된다. 편하지? -> 사용자가 게시판 처음들어오면 페이지 선택안하잖아. 그러면 그냥 PageaDTO객체를 하나 생성해서 건네줄꺼고, 사용자가 특정 페이지를 요청하면 파라미터가 날라온다. Page=5&size=10 이런식으로. 이 두개의 파라미터 값을 PageDTO가 받겠다.
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

        PostListResponseDTO dto = postService.getPosts(pageDTO); //postService가 getPosts를 할 때, 그 get값을 PostListResponseDTO dto 로 리턴을 한다.
        //저 PostListResponseDTO 클래스 가보면, 3가지가 있다. dto에는 3가지 정보들이 다 들어가있다.


        return ResponseEntity.ok()  //OK(200)줄거고 바디에다가는 PostListResponseDTO를 전달할거다. 그럼 잭슨은 응답객체 BODY에 세팅하고 이걸 클라이언트단이 받아서 화면에 표현한다!
                .body(dto);
    }


    //게시물 개별 조회(게시물 1개가져올것)
    @GetMapping("/{id}") //id는 pathVariable로 뜯어오자.
    public ResponseEntity<?> detail(@PathVariable long id) {
        log.info("/api/v1/post/{}: GET", id); //로그간단히찍어보자.

        try {
            PostDetailResponseDTO dto = postService.getDetail(id); //겟디테일한테 id주자. 아직 겟디테일 완성하진 않았지만 리턴타입이 있겠지? 뭘로리턴해? 하나에 게시물에 대한 정보를 담는 PostDetaiResponseDTO가 되겠지. 아깐 이거를 List를 받고 postListreponseDTO로했었다. 근데 지금은 1개다.   아무튼, getDetail(id);에 빨간불 들어오니 완성하자. 알트엔터로 만들면 -> PostService에 개별조회 메서드 생김. 가서작성해주자.
            //만약 에러 발생하지 않았다면(문제없다면) 리턴을 하겠다.
            return ResponseEntity.ok().body(dto); //겟디테일을 부르는 과정에서 문제없다면 이렇게 처리하겠다.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //게시글이 이상하게 왔다던지 그러면..클라이언트쪽에 알려주겠다. 바디에다가는 서비스에 작성한 "번 게시물이 존재하지 않습니다."를 클라이언트에다가 알려주고 싶다.
        }

        //예외터져도 동작할 수 있도록, getDetail부르는 곳을 트라이캐치로하자.(컨트롤 알트 t) -> 그럼 위처럼 트라치 캐치가됨.
        //만약 에러 발생하지 않았다면(문제없다면) 리턴을 하겠다.
    }


    //등록
    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody PostCreateDTO dto, BindingResult result) { //리퀘스트바디붙여야 자바객체타입으로 변환되어 받을수있음
        log.info("/api/v1/posts: POST방식으로온다! - payload: {}", dto); //payload는 전달되는 정보값이다. 파라미터와는 좀 다르다. 바디에 전송되는 값을 페이로드라고한다.
        //이제 PostCreateDTO 클래스를 만들어주자

        //간단한..검증로직써볼까
        if (dto == null) {
            return ResponseEntity.badRequest()
                    .body("등록 게시물 정보를 전달해주세요!");
        }

        //만약 result가 에러발견했다?
        if (result.hasErrors()) { //트루면, 입력값 검증에 걸린 것이다. 안걸리면 해즈에러는 false로 통과된다.
            //에러의 정보를 받아보자
            List<FieldError> fieldErrors = result.getFieldErrors();
            //어떤 입력값에 걸렸는지 출력 다 해보자
            fieldErrors.forEach(err -> { //객체가 err에 쌓일때마다 로그찍자
                log.warn("invalid client data - {}", err.toString());
            });
            //문제있다. 검증에 걸렸다고 클라이언트에다가도 말해줘야지
            return ResponseEntity.badRequest()
                    .body(fieldErrors); //바디에 담아서 전달한다.
        }

        //if문에 걸리지 않았다면 postservice에 인서트를 부르면서 클라이언트로부터 전달받은 dto를 전달하자
        PostDetailResponseDTO responseDTO = postService.insert(dto); //지금 메서드 없으니 선언해야됨. -> 인서트 되는 데이터를 다시 클라이언트로 전달할 것이다. 싱글페이지어플리케이션이잖아 우리는~ 리다이렉트써도되긴해.


    }


}



