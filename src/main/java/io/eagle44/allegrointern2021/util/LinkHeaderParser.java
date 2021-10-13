package io.eagle44.allegrointern2021.util;

import io.eagle44.allegrointern2021.repo.Pagination;

import java.util.List;

public class LinkHeaderParser {
    public static Pagination parse(List<String> linkHeader) {
        String[] links = linkHeader.get(0).split(", ");

        String prevPage = null;
        String nextPage = null;
        String firstPage = null;
        String lastPage = null;

        for (String link : links) {
            if (link.contains("prev")) {
                prevPage = link.substring(1, link.indexOf(">; rel="));
            } else if (link.contains("next")) {
                nextPage = link.substring(1, link.indexOf(">; rel="));
            } else if (link.contains("first")) {
                firstPage = link.substring(1, link.indexOf(">; rel="));
            } else if (link.contains("last")) {
                lastPage = link.substring(1, link.indexOf(">; rel="));
            }
        }

        return new Pagination(prevPage, nextPage, firstPage, lastPage);
    }
}
