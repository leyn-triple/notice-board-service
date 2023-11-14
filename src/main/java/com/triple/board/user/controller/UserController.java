package com.triple.board.user.controller;

import com.triple.board.exception.UserNotFoundException;
import com.triple.board.notice.dto.ResponseNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.user.dto.AddUserDto;
import com.triple.board.user.dto.ResponseUserDto;
import com.triple.board.user.dto.UpdateUserDto;
import com.triple.board.user.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @PostMapping("/api/user")
  public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDto addUserDto, Errors errors) {
    return userService.addUser(addUserDto, errors);
  }

  @PutMapping("/api/user/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto, Errors errors) {
    return userService.updateUser(id, updateUserDto, errors);
  }

  @GetMapping("/api/user/{id}")
  public ResponseUserDto getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @GetMapping("/api/user/{id}/notice")
  public List<ResponseNoticeDto> getUserNotice(@PathVariable Long id) {
    return userService.getUserNotice(id);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handlerUserNotFoundException(UserNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
