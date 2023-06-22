package org.vosiievska.exception;

public class KafkaBrokerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public KafkaBrokerException(String message) {
    super(message);
  }

  public KafkaBrokerException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public KafkaBrokerException(String message, Object... args) {
    super(String.format(message, args));
  }

}
