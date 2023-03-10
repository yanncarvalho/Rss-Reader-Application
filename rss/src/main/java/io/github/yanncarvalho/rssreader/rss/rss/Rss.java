package io.github.yanncarvalho.rssreader.rss.rss;

import java.time.LocalDateTime;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.github.yanncarvalho.rssreader.rss.rss.rsselements.Channel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@Document("rss")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"id", "originalLink", "lastUpdate", "channel"})
public class Rss {

   @MongoId
   @JsonIgnore
   @XmlTransient
   private ObjectId id;

   @XmlTransient
   @Indexed(unique = true)
   private String originalLink;

   @XmlTransient
   private LocalDateTime lastUpdate;

   private Channel channel;

   public Rss() {
      lastUpdate = LocalDateTime.now();
   }

   public LocalDateTime getLastUpdate() {
      return lastUpdate;
   }

   public void setLastUpdate(LocalDateTime lastUpdate) {
       this.lastUpdate = lastUpdate;
   }

   public String getOriginalLink() {
       return originalLink;
   }

   public void setOriginalLink(String originalLink) {
       this.originalLink = originalLink;
   }

   public Channel getChannel() {
       return channel;
    }

   public void setChannel(Channel channel) {
       this.channel = channel;
   }

   public ObjectId getId() {
   	 return id;
   }

   public void setId(ObjectId id) {
     this.id = id;
   }

   @Override
   public int hashCode() {
	   if(id == null) {
		   return super.hashCode();
	   }
	   return Objects.hash(id);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rss other = (Rss) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Rss [id=" + id + ", originalLink=" + originalLink + ", lastUpdate=" + lastUpdate + ", channel="
				+ channel + "]";
	}

}
