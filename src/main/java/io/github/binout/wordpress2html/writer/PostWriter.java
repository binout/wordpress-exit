/*
 * Copyright 2014 Benoît Prioux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.binout.wordpress2html.writer;

import io.github.binout.wordpress2html.Post;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;

public class PostWriter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final File file;
    private final Post post;

    public PostWriter(File output, Post post) {
        this.post = post;
        file = new File(output, getFilename(this.post) + ".html");
    }

    public File write() throws IOException {
        String htmlContent = getFullHtml();
        Files.copy(new ByteArrayInputStream(htmlContent.getBytes("UTF-8")), file.toPath());
        return file;
    }

    private String getFullHtml() {
        return "<html><head>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />" +
                "</head>" +
                "<body>" + post.getHtmlContent().replace("\n", "</p><p>") +
                "</body></html>";
    }

    private static String getFilename(Post p) {
        String name = p.getDate().format(DATE_TIME_FORMATTER) + "-" + p.getTitle();
        return name.replaceAll("[^a-zA-Z0-9.-]", "-");
    }

}
