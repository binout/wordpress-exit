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

import com.beust.jcommander.JCommander;
import io.github.binout.wordpress2html.extractor.PostExtractor;
import io.github.binout.wordpress2html.writer.PostWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public class WordPressExit {

    public static void exit(InputStream inputStream, File output, boolean asciidoc, Consumer<String> logger) throws Exception {
        long begin = System.currentTimeMillis();
        logger.accept("BEGIN");

        logger.accept("Begin extraction of posts...");
        List<Post> posts = new PostExtractor(inputStream).getPosts();
        logger.accept("Find " + posts.size() + " posts");

        output.mkdirs();
        logger.accept("Begin writing html posts...");
        posts.stream().forEach(p -> {
            try {
                File file = new PostWriter(output, p, asciidoc).write();
                logger.accept("Write " + file.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        logger.accept("END : duration=" + (System.currentTimeMillis() - begin) + " ms");
    }

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Arguments();
        new JCommander(arguments, args);

        exit(new FileInputStream(arguments.file), arguments.output, Boolean.parseBoolean(arguments.asciidoc), System.out::println);
    }

}
