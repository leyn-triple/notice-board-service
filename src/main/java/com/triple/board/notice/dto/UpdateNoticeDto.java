package com.triple.board.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateNoticeDto {

  //제목, 내용 입력
  private String title;
  private String contents;

}
