package com.triple.board.exception;

public class ExistsEmailException extends RuntimeException{

  public ExistsEmailException(String message) {
    super(message);
  }

}
