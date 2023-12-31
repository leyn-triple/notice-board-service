package com.triple.board.notice.service;

import com.triple.board.config.ResponseError;
import com.triple.board.exception.AlreadyDeletedException;
import com.triple.board.exception.DuplicateNoticeException;
import com.triple.board.exception.NoticeNotFoundException;
import com.triple.board.notice.dto.AddNoticeDto;
import com.triple.board.notice.dto.UpdateNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.repository.NoticeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Service
public class NoticeService {

  private final NoticeRepository noticeRepository;

  public ResponseEntity<?> addNotice(AddNoticeDto addNoticeDto, Errors errors) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrors = new ArrayList<>();

      errors.getAllErrors().forEach(e -> {
        responseErrors.add(ResponseError.of(e));
      });

      return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
    }

    LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);
    int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
        addNoticeDto.getTitle(), addNoticeDto.getContents(), checkDate);
    if (noticeCount > 0) {
      throw new DuplicateNoticeException("1분 이내에 등록된 동일한 공지사항이 존재합니다.");
    }

    Notice notice = Notice.builder()
        .title(addNoticeDto.getTitle())
        .contents(addNoticeDto.getContents())
        .regDate(LocalDateTime.now())
        .likes(0)
        .hits(0)
        .build();
    noticeRepository.save(notice);

    return ResponseEntity.ok().build();
  }

  public Notice getNotice(Long id) {
    Optional<Notice> notice = noticeRepository.findById(id);
    if(notice.isPresent()) {
      return notice.get();
    }
    return null;
  }

  public Page<Notice> noticeLatest(int size) {

    Page<Notice> noticePage = noticeRepository.findAll(
        PageRequest.of(0, size, Direction.DESC, "regDate"));

    return noticePage;
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

  public Notice deleteAllNotice(Long id) {
    noticeRepository.deleteAll();

    return null;
  }
}
