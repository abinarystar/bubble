package com.abinarystar.bubble.web.controller;

import com.abinarystar.bubble.command.user.CreateUserCommand;
import com.abinarystar.bubble.command.user.DeleteUserCommand;
import com.abinarystar.bubble.command.user.GetUserCommand;
import com.abinarystar.bubble.web.model.user.CreateUserWebRequest;
import com.abinarystar.bubble.web.model.user.CreateUserWebResponse;
import com.abinarystar.bubble.web.model.user.GetUserWebResponse;
import com.abinarystar.core.command.CommandExecutor;
import com.abinarystar.core.web.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

  private final CommandExecutor commandExecutor;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response<CreateUserWebResponse> createUser(@RequestBody CreateUserWebRequest request) {
    CreateUserWebResponse response = commandExecutor.execute(CreateUserCommand.class, request);
    return Response.ok(response);
  }

  @GetMapping("/{id}")
  public Response<GetUserWebResponse> getUser(@PathVariable String id) {
    GetUserWebResponse response = commandExecutor.execute(GetUserCommand.class, id);
    return Response.ok(response);
  }

  @DeleteMapping("/{id}")
  public Response<Void> deleteUser(@PathVariable String id) {
    commandExecutor.execute(DeleteUserCommand.class, id);
    return Response.ok();
  }
}
