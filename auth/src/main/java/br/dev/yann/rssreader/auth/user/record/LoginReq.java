package br.dev.yann.rssreader.auth.user.record;

import org.bouncycastle.util.Strings;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(
	  @NotBlank
	  String username,		
	  @NotBlank
	  String password) {
	
	  public LoginReq(String username, String password) {
	     this.username = Strings.toUpperCase(username);
	     this.password = password;
	  }
}
