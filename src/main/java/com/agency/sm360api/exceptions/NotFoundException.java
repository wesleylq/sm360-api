package com.agency.sm360api.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
  public NotFoundException(UUID id) {
    super(String.format("Could not find entry with Id: %s", id));
	}
}
