package br.dev.yann.rssreader.rss.rss;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.dev.yann.rssreader.rss.rss.elements.Channel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"originalLink", "content"})
@Document("rss")
public class Rss {
	
   @Id
   @XmlTransient
   public String id;

   @XmlTransient
   private String originalLink;

   @JsonProperty ("content")
   @Field("content")
   private Channel channel;

   public Rss() {}

   /**
    * @return String return the originalLink
    */
   public String getOriginalLink() {
       return originalLink;
   }

   /**
    * @param originalLink the originalLink to set
    */
   public void setOriginalLink(String originalLink) {
       this.originalLink = originalLink;
   }
   /**
    * @return Channel return the channel
    */
   public Channel getChannel() {
       return channel;
    }

   /**
    * @param channel the channel to set
    */
   public void setChannel(Channel channel) {
       this.channel = channel;
   }
    
   public String getId() {
   	 return id;
   }

   public void setId(String id) {
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
		return "Rss [id=" + id + ", originalLink=" + originalLink + ", channel=" + channel + "]";
	}



}
