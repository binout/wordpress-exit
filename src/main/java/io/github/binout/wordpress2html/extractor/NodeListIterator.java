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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.NoSuchElementException;

class NodeListIterator implements Iterator<Node> {

    private final NodeList nodeList;
    private final int size;
    private int index = 0;

    public NodeListIterator(NodeList nodeList) {
        this.nodeList = nodeList;
        this.size = nodeList.getLength();
    }


    @Override
    public boolean hasNext() {
        return size > index;
    }

    @Override
    public Node next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more item in the list");
        }
        Node node = nodeList.item(index);
        index += 1;
        return node;
    }
}
