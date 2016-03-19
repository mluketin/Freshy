package com.akatsuki.freshy;

import java.util.List;

public class ActionBig extends ActionBase {
  private List<String> words;
  private String name = null;
  private boolean image;
  private boolean audio;
  private boolean video;
  private boolean link;
  private boolean text;
  private boolean status;

  public ActionBig(
          String deviceId,
          String url,
          List<String> words,
          String name,
          boolean image,
          boolean audio,
          boolean video,
          boolean link,
          boolean text, boolean satatus) {
    super(deviceId, url);
    this.words = words;
    this.name = name;
    this.image = image;
    this.audio = audio;
    this.video = video;
    this.link = link;
    this.text = text;
    this.status = status;
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

  public String getName() {
    return name;
  }

  public boolean isText() {
    return text;
  }
  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void setText(boolean text) {
    this.text = text;
  }

  public void setName(String name) {
    this.name = name;
  }


  public void setWords(List<String> words) {
    this.words = words;
  }

  public void setImage(boolean image) {
    this.image = image;
  }

  public void setAudio(boolean audio) {
    this.audio = audio;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public void setLink(boolean link) {
    this.link = link;
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
