package br.dev.yann.rssreader.rss.rss.elements;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import br.dev.yann.rssreader.rss.rss.elements.channel.elements.Cloud;
import br.dev.yann.rssreader.rss.rss.elements.channel.elements.Item;
import br.dev.yann.rssreader.rss.rss.elements.channel.elements.TextInput;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {

  private String title;
  private String description;
  private String link;
  private String language;
  private String copyright;
  private String managingEditor;
  private String webMaste;
  private String pubDate;
  private String lastBuildDate;
  private String category;
  private String docs;
  private String ttl;
  private String rating;
  private String skipHours;
  private String skipDays;

  @XmlElement
  private Image image;

  @XmlElement(name = "item")
  private List<Item> items = new ArrayList<>();

  @XmlElement
  private TextInput textInput;

  @XmlElement
  private Cloud cloud;

  public Channel() {
  }

  /**
   * @return String return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return String return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return String return the link
   */
  public String getLink() {
    return link;
  }

  /**
   * @param link the link to set
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * @return String return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * @param language the language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * @return String return the copyright
   */
  public String getCopyright() {
    return copyright;
  }

  /**
   * @param copyright the copyright to set
   */
  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  /**
   * @return String return the managingEditor
   */
  public String getManagingEditor() {
    return managingEditor;
  }

  /**
   * @param managingEditor the managingEditor to set
   */
  public void setManagingEditor(String managingEditor) {
    this.managingEditor = managingEditor;
  }

  /**
   * @return String return the webMaste
   */
  public String getWebMaste() {
    return webMaste;
  }

  /**
   * @param webMaste the webMaste to set
   */
  public void setWebMaste(String webMaste) {
    this.webMaste = webMaste;
  }

  /**
   * @return String return the pubDate
   */
  public String getPubDate() {
    return pubDate;
  }

  /**
   * @param pubDate the pubDate to set
   */
  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  /**
   * @return String return the lastBuildDate
   */
  public String getLastBuildDate() {
    return lastBuildDate;
  }

  /**
   * @param lastBuildDate the lastBuildDate to set
   */
  public void setLastBuildDate(String lastBuildDate) {
    this.lastBuildDate = lastBuildDate;
  }

  /**
   * @return String return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @return String return the docs
   */
  public String getDocs() {
    return docs;
  }

  /**
   * @param docs the docs to set
   */
  public void setDocs(String docs) {
    this.docs = docs;
  }

  /**
   * @return String return the ttl
   */
  public String getTtl() {
    return ttl;
  }

  /**
   * @param ttl the ttl to set
   */
  public void setTtl(String ttl) {
    this.ttl = ttl;
  }

  /**
   * @return String return the rating
   */
  public String getRating() {
    return rating;
  }

  /**
   * @param rating the rating to set
   */
  public void setRating(String rating) {
    this.rating = rating;
  }

  /**
   * @return String return the skipHours
   */
  public String getSkipHours() {
    return skipHours;
  }

  /**
   * @param skipHours the skipHours to set
   */
  public void setSkipHours(String skipHours) {
    this.skipHours = skipHours;
  }

  /**
   * @return String return the skipDays
   */
  public String getSkipDays() {
    return skipDays;
  }

  /**
   * @param skipDays the skipDays to set
   */
  public void setSkipDays(String skipDays) {
    this.skipDays = skipDays;
  }

  /**
   * @return Image return the image
   */
  public Image getImage() {
    return image;
  }

  /**
   * @param image the image to set
   */
  public void setImage(Image image) {
    this.image = image;
  }

  /**
   * @return List<Item> return the items
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * @param items the items to set
   */
  public void setItems(List<Item> items) {
    this.items = items;
  }

  /**
   * @return TextInput return the textInput
   */
  public TextInput getTextInput() {
    return textInput;
  }

  /**
   * @param textInput the textInput to set
   */
  public void setTextInput(TextInput textInput) {
    this.textInput = textInput;
  }

  /**
   * @return Cloud return the cloud
   */
  public Cloud getCloud() {
    return cloud;
  }

  /**
   * @param cloud the cloud to set
   */
  public void setCloud(Cloud cloud) {
    this.cloud = cloud;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((link == null) ? 0 : link.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + ((copyright == null) ? 0 : copyright.hashCode());
    result = prime * result + ((managingEditor == null) ? 0 : managingEditor.hashCode());
    result = prime * result + ((webMaste == null) ? 0 : webMaste.hashCode());
    result = prime * result + ((pubDate == null) ? 0 : pubDate.hashCode());
    result = prime * result + ((lastBuildDate == null) ? 0 : lastBuildDate.hashCode());
    result = prime * result + ((category == null) ? 0 : category.hashCode());
    result = prime * result + ((docs == null) ? 0 : docs.hashCode());
    result = prime * result + ((ttl == null) ? 0 : ttl.hashCode());
    result = prime * result + ((rating == null) ? 0 : rating.hashCode());
    result = prime * result + ((skipHours == null) ? 0 : skipHours.hashCode());
    result = prime * result + ((skipDays == null) ? 0 : skipDays.hashCode());
    result = prime * result + ((image == null) ? 0 : image.hashCode());
    result = prime * result + ((items == null) ? 0 : items.hashCode());
    result = prime * result + ((textInput == null) ? 0 : textInput.hashCode());
    result = prime * result + ((cloud == null) ? 0 : cloud.hashCode());
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
    Channel other = (Channel) obj;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (link == null) {
      if (other.link != null)
        return false;
    } else if (!link.equals(other.link))
      return false;
    if (language == null) {
      if (other.language != null)
        return false;
    } else if (!language.equals(other.language))
      return false;
    if (copyright == null) {
      if (other.copyright != null)
        return false;
    } else if (!copyright.equals(other.copyright))
      return false;
    if (managingEditor == null) {
      if (other.managingEditor != null)
        return false;
    } else if (!managingEditor.equals(other.managingEditor))
      return false;
    if (webMaste == null) {
      if (other.webMaste != null)
        return false;
    } else if (!webMaste.equals(other.webMaste))
      return false;
    if (pubDate == null) {
      if (other.pubDate != null)
        return false;
    } else if (!pubDate.equals(other.pubDate))
      return false;
    if (lastBuildDate == null) {
      if (other.lastBuildDate != null)
        return false;
    } else if (!lastBuildDate.equals(other.lastBuildDate))
      return false;
    if (category == null) {
      if (other.category != null)
        return false;
    } else if (!category.equals(other.category))
      return false;
    if (docs == null) {
      if (other.docs != null)
        return false;
    } else if (!docs.equals(other.docs))
      return false;
    if (ttl == null) {
      if (other.ttl != null)
        return false;
    } else if (!ttl.equals(other.ttl))
      return false;
    if (rating == null) {
      if (other.rating != null)
        return false;
    } else if (!rating.equals(other.rating))
      return false;
    if (skipHours == null) {
      if (other.skipHours != null)
        return false;
    } else if (!skipHours.equals(other.skipHours))
      return false;
    if (skipDays == null) {
      if (other.skipDays != null)
        return false;
    } else if (!skipDays.equals(other.skipDays))
      return false;
    if (image == null) {
      if (other.image != null)
        return false;
    } else if (!image.equals(other.image))
      return false;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    if (textInput == null) {
      if (other.textInput != null)
        return false;
    } else if (!textInput.equals(other.textInput))
      return false;
    if (cloud == null) {
      if (other.cloud != null)
        return false;
    } else if (!cloud.equals(other.cloud))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Channel [title=" + title + ", description=" + description + ", link=" + link + ", language=" + language
        + ", copyright=" + copyright + ", managingEditor=" + managingEditor + ", webMaste=" + webMaste + ", pubDate="
        + pubDate + ", lastBuildDate=" + lastBuildDate + ", category=" + category + ", docs=" + docs + ", ttl=" + ttl
        + ", rating=" + rating + ", skipHours=" + skipHours + ", skipDays=" + skipDays + ", image=" + image
        + ", items=" + items + ", textInput=" + textInput + ", cloud=" + cloud + "]";
  }

}
