package com.triple.board.exception;

public class PasswordNotMatchException extends RuntimeException{

  public PasswordNotMatchException(String message) {
    super(message);
  }

}
