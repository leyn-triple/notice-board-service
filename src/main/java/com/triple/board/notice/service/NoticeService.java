package com.triple.board.notice.service;

import com.triple.board.notice.dto.AddNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.repository.NoticeRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeService {

  private final NoticeRepository noticeRepository;

  public Notice addNotice(AddNoticeDto addNoticeDto) {
    Notice notice = Notice.builder()
        .title(addNoticeDto.getTitle())
        .contents(addNoticeDto.getContents())
        .regDate(LocalDateTime.now())
        .likes(0)
        .hits(0)
        .build();
    noticeRepository.save(notice);

    return notice;
  }
}
