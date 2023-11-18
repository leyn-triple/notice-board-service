package com.triple.board.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.triple.board.exception.ExistsEmailException;
import com.triple.board.exception.PasswordNotMatchException;
import com.triple.board.exception.UserNotFoundException;
import com.triple.board.notice.dto.ResponseNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.entity.NoticeLike;
import com.triple.board.user.dto.AddUserDto;
import com.triple.board.user.dto.FindUserDto;
import com.triple.board.user.dto.ResponseUserDto;
import com.triple.board.user.dto.UpdateUserDto;
import com.triple.board.user.dto.UserLoginDto;
import com.triple.board.user.dto.UserPasswordDto;
import com.triple.board.user.entity.User;
import com.triple.board.user.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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

  @GetMapping("/api/userFind")
  public ResponseEntity<?> findUser(@RequestBody FindUserDto findUserDto) {
    return userService.findUser(findUserDto);
  }

  @GetMapping("/api/user/{id}/notice")
  public List<ResponseNoticeDto> getUserNotice(@PathVariable Long id) {
    return userService.getUserNotice(id);
  }

  @PatchMapping("/api/user/{id}/password")
  public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDto userPasswordDto, Errors errors) {
    return userService.updateUserPassword(id, userPasswordDto, errors);
  }
  @DeleteMapping("/api/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id);
  }
  @GetMapping("/api/user/{id}/password/reset")
  public ResponseEntity<?> resetUserPassword(@PathVariable Long id) {
    return userService.resetUserPassword(id);
  }

  @GetMapping("/api/user/{id}/notice/like")
  public List<NoticeLike> likeNotice(@PathVariable Long id) {
    return userService.likeNotice(id);

  }

  @PostMapping("api/user/login")
  public ResponseEntity<?> createToken(@RequestBody @Valid UserLoginDto userLoginDto, Errors errors) {
    return userService.createToken(userLoginDto, errors);
  }

  @PatchMapping("/api/user/login")
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    return userService.refreshToken(request);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handlerUserNotFoundException(UserNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExistsEmailException.class)
  public ResponseEntity<String> handlerExistsEmailException(ExistsEmailException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PasswordNotMatchException.class)
  public ResponseEntity<String> handlerPasswordNotMatchException(PasswordNotMatchException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
