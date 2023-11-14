package com.triple.board.notice.dto;

import com.triple.board.notice.entity.Notice;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class ResponseNoticeDto {

  private long id;
  private long regUserId;
  private String regUserName;

  private String title;
  private String contents;
  private LocalDateTime regDate;
  private LocalDateTime updateDate;
  private long hits;
  private long likes;

  public static ResponseNoticeDto of(Notice notice) {
    return ResponseNoticeDto.builder()
            .id(notice.getId())
            .title(notice.getTitle())
            .contents(notice.getContents())
            .regDate(notice.getRegDate())
            .regUserId(notice.getUser().getId())
            .regUserName(notice.getUser().getUserName())
            .updateDate(notice.getUpdateDate())
            .hits(notice.getHits())
            .likes(notice.getLikes())
            .build();
  }

}
