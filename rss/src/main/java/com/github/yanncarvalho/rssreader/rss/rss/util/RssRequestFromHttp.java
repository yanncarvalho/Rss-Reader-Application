package com.github.yanncarvalho.rssreader.rss.rss.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;


@Component
public class RssRequestFromHttp {

  @Autowired
  @Qualifier("okHttpClient")
  private OkHttpClient client;

  
  public InputStream getXml(String url) throws IOException {

    var request = new Request.Builder().url(url)
                                      .header("Content-Type", "application/xml")
                                      .build();
    var response = this.client.newCall(request).execute();
    return response.body().byteStream();
  }
}









