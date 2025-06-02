package com.toy.project.board.exception;

public class CommentNotFoundException extends RuntimeException {
  public CommentNotFoundException(String message) {
    super(message);
  }
}
