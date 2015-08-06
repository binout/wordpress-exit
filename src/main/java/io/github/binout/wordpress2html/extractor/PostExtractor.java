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
package io.github.binout.wordpress2html.extractor;

import io.github.binout.wordpress2html.Post;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PostExtractor {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final List<Post> posts;

    public PostExtractor(InputStream inputStream) throws Exception {
        NodeList articles = getWordPressPosts(inputStream);
        posts = DomUtils.streamOf(articles)
                .filter(PostExtractor::isPost)
                .map(n -> {
                    String title = DomUtils.findChildTextContent(n, "title");
                    LocalDateTime dateTime = LocalDateTime.parse(DomUtils.findChildTextContent(n, "wp:post_date"), DATE_TIME_FORMATTER);
                    String content = DomUtils.findChildTextContent(n, "content:encoded");
                    return new Post(title, content, dateTime);

                }).collect(Collectors.toList());
    }

    public List<Post> getPosts() {
        return posts;
    }

    static NodeList getWordPressPosts(InputStream file) throws XPathFactoryConfigurationException, FileNotFoundException, XPathExpressionException {
        return DomUtils.getNodeList(new InputStreamReader(file), "//*[local-name()='item']");
    }

    private static boolean isPost(Node n) {
        return DomUtils.findChildTextContent(n, "wp:post_type").equals("post");
    }

}
