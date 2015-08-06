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
package io.github.binout.wordpress2html;

import java.time.LocalDateTime;

public class Post {

    private final String title;
    private final String htmlContent;
    private final LocalDateTime date;

    public Post(String title, String htmlContent, LocalDateTime date) {
        this.title = title;
        this.htmlContent = htmlContent;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
