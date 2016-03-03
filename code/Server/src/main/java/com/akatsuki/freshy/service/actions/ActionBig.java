package com.akatsuki.freshy.service.actions;

import java.util.List;

public class ActionBig extends ActionBase{
  private final List<String> words;
  private final boolean image;
  private final boolean audio;
  private final boolean video;
  private final boolean link;

  public ActionBig(
      String deviceId,
      String url,
      List<String> words,
      boolean image,
      boolean audio,
      boolean video,
      boolean link) {
    super(deviceId, url);
    this.words = words;
    this.image = image;
    this.audio = audio;
    this.video = video;
    this.link = link;
  }

  public List<String> getWords() {
    return words;
  }

  public boolean isImage() {
    return image;
  }

  public boolean isAudio() {
    return audio;
  }

  public boolean isVideo() {
    return video;
  }

  public boolean isLink() {
    return link;
  }

  @Override
  public String toString() {
    return String.format("ActionBig(%s, %s, %s, %s, %s, %s, %s)", getDeviceId(), getUrl(), words, image, audio, video, link);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (audio ? 1231 : 1237);
    result = prime * result + (image ? 1231 : 1237);
    result = prime * result + (link ? 1231 : 1237);
    result = prime * result + (video ? 1231 : 1237);
    result = prime * result + ((words == null) ? 0 : words.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ActionBig other = (ActionBig) obj;
    if (audio != other.audio)
      return false;
    if (image != other.image)
      return false;
    if (link != other.link)
      return false;
    if (video != other.video)
      return false;
    if (words == null) {
      if (other.words != null)
        return false;
    } else if (!words.equals(other.words))
      return false;
    return true;
  }
}
