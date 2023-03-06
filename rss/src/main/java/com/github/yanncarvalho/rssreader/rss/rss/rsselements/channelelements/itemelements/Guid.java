package com.github.yanncarvalho.rssreader.rss.rss.rsselements.channelelements.itemelements;

import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class Guid {

  @XmlAttribute
  private String isPermaLink;

  @XmlElement
  private String guid;
  
  public Guid() {}

  public String getIsPermaLink() {
		return isPermaLink;
  }

  public void setIsPermaLink(String isPermaLink) {
	this.isPermaLink = isPermaLink;
  }
  
  public String getGuid() {
	return guid;
  }

  public void setGuid(String guid) {
	this.guid = guid;
  }  

  @Override
  public int hashCode() {
  	return Objects.hash(guid, isPermaLink);
  }

  @Override
  public boolean equals(Object obj) {
  	if (this == obj)
  		return true;
  	if (obj == null)
  		return false;
  	if (getClass() != obj.getClass())
  		return false;
  	Guid other = (Guid) obj;
  	return Objects.equals(guid, other.guid) && Objects.equals(isPermaLink, other.isPermaLink);
  }

  @Override
  public String toString() {
	 return "Guid [isPermaLink=" + isPermaLink + ", guid=" + guid + "]";
  }

}
