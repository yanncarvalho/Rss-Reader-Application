package br.dev.yann.rssreader.rss.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {

   @Id
   private UUID userId;

   private List<String> urls;
   
   public User(UUID userId) {
       this.userId = userId;
       this.urls = new ArrayList<>();
   }
   
   public UUID getUserId() {
	return userId;
   }	

   public void setUserId(UUID userId) {
	this.userId = userId;
   }

   public List<String> getUrls() {
	return Collections.unmodifiableList(urls);
   }	

   public void setUrls(List<String> urls) {
	this.urls = urls;
   }

   @Override
   public int hashCode() {
	return Objects.hash(userId);
   }

   @Override
   public boolean equals(Object obj) {
	if (this == obj)
	    return true;
       if (obj == null)
	   return false;
       if (getClass() != obj.getClass())
	   return false;
        User other = (User) obj;
	return Objects.equals(userId, other.userId);
    }

   @Override
   public String toString() {
	   return "User [userId=" + userId + ", urls=" + urls + "]";
   }
  
}
