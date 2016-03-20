package com.akatsuki.freshy.model;

import java.io.Serializable;
import java.util.List;

public class ActionBig implements Serializable {
  private String url;
  private String words;
  private String name = null;
  private boolean image;
  private boolean audio;
  private boolean video;
  private boolean link;
  private boolean text;
  private boolean status;
  private String sha;

  public ActionBig(
          String url,
          String words,
          String name,
          boolean image,
          boolean audio,
          boolean video,
          boolean link,
          boolean text,
          boolean status) {
    this.url = url;
    this.words = words;
    this.name = name;
    this.image = image;
    this.audio = audio;
    this.video = video;
    this.link = link;
    this.text = text;
    this.status = status;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getWords() {
    return words;
  }

  public void setWords(String words) {
    this.words = words;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isImage() {
    return image;
  }

  public void setImage(boolean image) {
    this.image = image;
  }

  public boolean isAudio() {
    return audio;
  }

  public void setAudio(boolean audio) {
    this.audio = audio;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public boolean isLink() {
    return link;
  }

  public void setLink(boolean link) {
    this.link = link;
  }

  public boolean isText() {
    return text;
  }

  public void setText(boolean text) {
    this.text = text;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ActionBig actionBig = (ActionBig) o;

    if (image != actionBig.image) return false;
    if (audio != actionBig.audio) return false;
    if (video != actionBig.video) return false;
    if (link != actionBig.link) return false;
    if (text != actionBig.text) return false;
    if (status != actionBig.status) return false;
    if (url != null ? !url.equals(actionBig.url) : actionBig.url != null) return false;
    if (words != null ? !words.equals(actionBig.words) : actionBig.words != null) return false;
    return !(name != null ? !name.equals(actionBig.name) : actionBig.name != null);

  }

  @Override
  public int hashCode() {
    int result = url != null ? url.hashCode() : 0;
    result = 31 * result + (words != null ? words.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (image ? 1 : 0);
    result = 31 * result + (audio ? 1 : 0);
    result = 31 * result + (video ? 1 : 0);
    result = 31 * result + (link ? 1 : 0);
    result = 31 * result + (text ? 1 : 0);
    result = 31 * result + (status ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ActionBig{" +
            "url='" + url + '\'' +
            ", words=" + words +
            ", name='" + name + '\'' +
            ", image=" + image +
            ", audio=" + audio +
            ", video=" + video +
            ", link=" + link +
            ", text=" + text +
            ", status=" + status +
            '}';
  }
}
