package com.triple.board.notice.controller;

import com.triple.board.notice.dto.AddNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoticeController {

  private final NoticeService noticeService;

  @PostMapping("/api/notice")
  public Notice addNotice(@RequestBody AddNoticeDto addNoticeDto) {
    return noticeService.addNotice(addNoticeDto);
  }

}
