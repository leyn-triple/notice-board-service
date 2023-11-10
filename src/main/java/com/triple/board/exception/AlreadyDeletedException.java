package com.triple.board.exception;

public class AlreadyDeletedException extends RuntimeException{

  public AlreadyDeletedException(String message) {
    super(message);
  }

}
