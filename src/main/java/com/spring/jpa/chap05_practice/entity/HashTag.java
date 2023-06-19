package com.spring.jpa.chap05_practice.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"post"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hash_tag")
public class HashTag {


    //게시글 번호
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no") //객체는 id로 운영할꺼고 컬럼명은 post_no로 들어 갈 것이다.
    private long id;


    //해시태그 이름
    private String tagName;


    //HashTag가 연관관계 주인이니 post를 가지겠지.
    @ManyToOne(fetch = FetchType.LAZY)  //내가 많고 post가 1이니, ManyToOne이다. 전략도 LAZY로딩으로해주자.
    @JoinColumn(name = "post_no") //name에는 컬럼명들어가야함. 이거를 참조한다고 알려줘야함.
    private Post post;




}
