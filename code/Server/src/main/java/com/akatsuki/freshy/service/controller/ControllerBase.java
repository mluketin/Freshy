package com.akatsuki.freshy.service.controller;

import com.akatsuki.freshy.service.threads.ThreadManager;

public class ControllerBase {
  protected static ThreadManager threadManager = ThreadManager.getInstance();
}
