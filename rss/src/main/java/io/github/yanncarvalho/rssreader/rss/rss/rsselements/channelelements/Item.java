package io.github.yanncarvalho.rssreader.rss.rss.rsselements.channelelements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.yanncarvalho.rssreader.rss.rss.rsselements.channelelements.itemelements.Category;
import io.github.yanncarvalho.rssreader.rss.rss.rsselements.channelelements.itemelements.Enclosure;
import io.github.yanncarvalho.rssreader.rss.rss.rsselements.channelelements.itemelements.Guid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Channel Item element
 *
 * @author Yann Carvalho
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    private String title;
    private String link;
    private String descript;
    private String author;
    private String comments;
    private String description;

    @XmlElement
    private Enclosure enclosure;

    @XmlElement
    private Guid guid;

    private String pubDate;

    private String source;

    @XmlElement(name = "category")
    private List<Category> categories;

    public Item() {

	categories = new ArrayList<>();
	enclosure = new Enclosure();
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getLink() {
	return link;
    }

    public void setLink(String link) {
	this.link = link;
    }

    public String getDescript() {
	return descript;
    }

    public void setDescript(String descript) {
	this.descript = descript;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Enclosure getEnclosure() {
	return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
	this.enclosure = enclosure;
    }

    public Guid getGuid() {
	return guid;
    }

    public void setGuid(Guid guid) {
	this.guid = guid;
    }

    public String getPubDate() {
	return pubDate;
    }

    public void setPubDate(String pubDate) {
	this.pubDate = pubDate;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    public List<Category> getCategories() {
	return Collections.unmodifiableList(categories);
    }

    public void setCategories(List<Category> categories) {
	this.categories = categories;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((title == null) ? 0 : title.hashCode());
	result = prime * result + ((link == null) ? 0 : link.hashCode());
	result = prime * result + ((descript == null) ? 0 : descript.hashCode());
	result = prime * result + ((author == null) ? 0 : author.hashCode());
	result = prime * result + ((comments == null) ? 0 : comments.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((enclosure == null) ? 0 : enclosure.hashCode());
	result = prime * result + ((guid == null) ? 0 : guid.hashCode());
	result = prime * result + ((pubDate == null) ? 0 : pubDate.hashCode());
	result = prime * result + ((source == null) ? 0 : source.hashCode());
	result = prime * result + ((categories == null) ? 0 : categories.hashCode());
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
	Item other = (Item) obj;
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	if (link == null) {
	    if (other.link != null)
		return false;
	} else if (!link.equals(other.link))
	    return false;
	if (descript == null) {
	    if (other.descript != null)
		return false;
	} else if (!descript.equals(other.descript))
	    return false;
	if (author == null) {
	    if (other.author != null)
		return false;
	} else if (!author.equals(other.author))
	    return false;
	if (comments == null) {
	    if (other.comments != null)
		return false;
	} else if (!comments.equals(other.comments))
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (enclosure == null) {
	    if (other.enclosure != null)
		return false;
	} else if (!enclosure.equals(other.enclosure))
	    return false;
	if (guid == null) {
	    if (other.guid != null)
		return false;
	} else if (!guid.equals(other.guid))
	    return false;
	if (pubDate == null) {
	    if (other.pubDate != null)
		return false;
	} else if (!pubDate.equals(other.pubDate))
	    return false;
	if (source == null) {
	    if (other.source != null)
		return false;
	} else if (!source.equals(other.source))
	    return false;
	if (categories == null) {
	    if (other.categories != null)
		return false;
	} else if (!categories.equals(other.categories))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Item [title=" + title + ", link=" + link + ", descript=" + descript + ", author=" + author
		+ ", comments=" + comments + ", description=" + description + ", enclosure=" + enclosure + ", guid="
		+ guid + ", pubDate=" + pubDate + ", source=" + source + ", categories=" + categories + "]";
    }

}
