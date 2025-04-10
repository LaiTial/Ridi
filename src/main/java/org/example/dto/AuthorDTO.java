package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthorDTO {

    private String realName; // 본명
    private String penName; // 필명
}
