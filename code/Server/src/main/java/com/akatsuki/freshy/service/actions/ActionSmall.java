package com.akatsuki.freshy.service.actions;

public class ActionSmall extends ActionBase {

  public ActionSmall(String deviceId, String url) {
    super(deviceId, url);
  }

  @Override
  public String toString() {
    return String.format("ActionSmall(%s, %s)", getDeviceId(), getUrl());
  }
}
