package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateKeywordDTO {
    private String name; // 키워드 명
    private String type; // 장르, 소재, 직업, 주인공 등
    List<String> category; // 키워드가 저장될 카테고리 목록
}
