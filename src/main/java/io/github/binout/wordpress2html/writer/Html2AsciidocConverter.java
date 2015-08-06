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

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class Html2AsciidocConverter {

    public File convert(File html) throws IOException {
        String baseName = FilenameUtils.getBaseName(html.getName());
        File asciidoc = new File(html.getParentFile(), baseName + ".adoc");
        CommandLine cmdLine = CommandLine.parse("pandoc --no-wrap -f html -t asciidoc " + html.getAbsolutePath() + " -o " + asciidoc.getAbsolutePath());
        execute(cmdLine);
        return asciidoc;
    }

    private void execute(CommandLine cmdLine) throws IOException {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        executor.setWatchdog(watchdog);
        int exitValue = executor.execute(cmdLine);
        if (exitValue != 0) {
            throw new RuntimeException("Pandoc is not installed !");
        }
    }
}
