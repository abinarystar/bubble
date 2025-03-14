package com.abinarystar.core.command;

public interface Command<T, R> {

  R execute(T request);
}
