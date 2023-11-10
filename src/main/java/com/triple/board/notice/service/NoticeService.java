package com.triple.board.notice.service;

import com.triple.board.exception.AlreadyDeletedException;
import com.triple.board.exception.NoticeNotFoundException;
import com.triple.board.notice.dto.AddNoticeDto;
import com.triple.board.notice.dto.UpdateNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.repository.NoticeRepository;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

  public Notice getNotice(Long id) {
    Optional<Notice> notice = noticeRepository.findById(id);
    if(notice.isPresent()) {
      return notice.get();
    }
    return null;
  }

  public Notice updateNotice(Long id, UpdateNoticeDto updateNoticeDto) {

    Notice notice = noticeRepository.findById(id)
        .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

    notice.setTitle(updateNoticeDto.getTitle());
    notice.setContents(updateNoticeDto.getContents());
    notice.setUpdateDate(LocalDateTime.now());
    noticeRepository.save(notice);

    return notice;

  }

  public Notice hitsNotice(Long id) {
    Notice notice = noticeRepository.findById(id)
        .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

    notice.setHits(notice.getHits() + 1);
    noticeRepository.save(notice);

    return notice;
  }

  public Notice deleteNotice(Long id) {
    Notice notice = noticeRepository.findById(id)
        .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

    if(notice.isDeleted()) {
      throw new AlreadyDeletedException("이미 삭제 된 글입니다.");
    }
    notice.setDeleted(true);
    notice.setDeleteDate(LocalDateTime.now());
    noticeRepository.save(notice);

    return null;
  }
}
