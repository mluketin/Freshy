package com.akatsuki.freshy;

public class ActionBase {
  private  String deviceId;
  private  String url;

  public ActionBase(
      String deviceId,
      String url) {
    this.deviceId = deviceId;
    this.url = url;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getUrl() {
    return url;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public void setUrl(String url) {
    this.url = url;
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
