package br.dev.yann.rssreader.rss;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import io.github.yanncarvalho.rssreader.rss.RssApplication;

@SpringBootTest
@ActiveProfiles("test")	
@ContextConfiguration(classes = RssApplication.class)
class RssApplicationTests {

}
