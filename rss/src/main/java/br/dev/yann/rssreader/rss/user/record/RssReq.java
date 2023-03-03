package br.dev.yann.rssreader.rss.user.record;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotEmpty;

public record RssReq(
        @NotEmpty
       List<@URL(regexp = "^(http|https):\\/\\/.*") String> urls) {}
