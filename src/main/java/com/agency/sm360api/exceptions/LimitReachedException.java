package com.agency.sm360api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LimitReachedException extends Exception {
  public LimitReachedException() {
    super("Publish limit reached.");
  }
}
