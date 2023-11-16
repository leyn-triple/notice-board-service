package com.triple.board.notice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddNoticeDto {

  //제목, 내용 입력
  @Size(min = 10, max = 100, message = "제목은 10-100자 사이의 값입니다.")
  @NotBlank(message = "제목은 필수 항목입니다.")
  private String title;

  @Size(min = 50, max = 1000, message = "내용은 50-1000자 사이의 값입니다.")
  @NotBlank(message = "내용은 필수 항목입니다.")
  private String contents;

}
