package com.triple.board.notice.controller;

import com.triple.board.exception.AlreadyDeletedException;
import com.triple.board.exception.DuplicateNoticeException;
import com.triple.board.exception.NoticeNotFoundException;
import com.triple.board.notice.dto.AddNoticeDto;
import com.triple.board.notice.dto.UpdateNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.service.NoticeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoticeController {

  private final NoticeService noticeService;

  @PostMapping("/api/notice")
  public ResponseEntity<?> addNotice(@RequestBody @Valid AddNoticeDto addNoticeDto,  Errors errors) {
    return noticeService.addNotice(addNoticeDto, errors);
  }

  @GetMapping("/api/notice/{id}")
  public Notice getNotice(@PathVariable Long id) {
    return noticeService.getNotice(id);
  }

  @GetMapping("/api/notice/latest/{size}")
  public Page<Notice> noticeLatest(@PathVariable int size) {
    return noticeService.noticeLatest(size);
  }

  @PutMapping("/api/notice/{id}")
  public Notice updateNotice(@PathVariable Long id, @RequestBody UpdateNoticeDto updateNoticeDto) {
    return noticeService.updateNotice(id, updateNoticeDto);
  }

  @PatchMapping("/api/notice/{id}/hits")
  public Notice hitsNotice(@PathVariable Long id) {
    return noticeService.hitsNotice(id);
  }

  @DeleteMapping("/api/notice/{id}")
  public Notice deleteNotice(@PathVariable Long id) {
    return noticeService.deleteNotice(id);
  }

  @DeleteMapping("/api/notice/all")
  public Notice deleteAllNotice(@PathVariable Long id) {
    return noticeService.deleteAllNotice(id);
  }

  @ExceptionHandler(NoticeNotFoundException.class)
  public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AlreadyDeletedException.class)
  public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DuplicateNoticeException.class)
  public ResponseEntity<String> handlerDuplicateNoticeException(DuplicateNoticeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }


}
