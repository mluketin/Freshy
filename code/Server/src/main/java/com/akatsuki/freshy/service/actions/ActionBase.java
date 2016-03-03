package com.akatsuki.freshy.service.actions;

import com.akatsuki.freshy.service.utils.StringUtils;

public class ActionBase {
  private final String deviceId;
  private final String url;

  public ActionBase(
      String deviceId,
      String url) {
    this.deviceId = deviceId;
    this.url = url;
    validate();
  }

  private void validate() {
    StringUtils.notNullOrEmpty("templateName", deviceId);
    StringUtils.notNullOrEmpty("recieverName", url);
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ActionBase other = (ActionBase) obj;
    if (deviceId == null) {
      if (other.deviceId != null)
        return false;
    } else if (!deviceId.equals(other.deviceId))
      return false;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    return true;
  }
}
