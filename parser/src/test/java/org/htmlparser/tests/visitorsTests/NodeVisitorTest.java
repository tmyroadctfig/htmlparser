// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Somik Raha
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/test/java/org/htmlparser/tests/visitorsTests/NodeVisitorTest.java $
// $Author: derrickoswald $
// $Date: 2011-04-25 19:39:12 +1000 (Mon, 25 Apr 2011) $
// $Revision: 74 $
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the Common Public License; either
// version 1.0 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// Common Public License for more details.
//
// You should have received a copy of the Common Public License
// along with this library; if not, the license is available from
// the Open Source Initiative (OSI) website:
//   http://opensource.org/licenses/cpl1.0.php

package org.htmlparser.tests.visitorsTests;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.visitors.NodeVisitor;

public class NodeVisitorTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.visitorsTests.NodeVisitorTest", "NodeVisitorTest");
    }

    public NodeVisitorTest(String name) {
        super(name);
    }

    public void testVisitTag() throws Exception {
        ParameterVisitor visitor = new ParameterVisitor();
        createParser(
            "<input>" +
                "<param name='key1'>value1</param>"+
                "<param name='key2'>value2</param>"+
            "</input>"
        );
        parser.visitAllNodesWith(visitor);
        assertEquals("value of key1","value1",visitor.getValue("key1"));
        assertEquals("value of key2","value2",visitor.getValue("key2"));
    }

    class ParameterVisitor extends NodeVisitor {
        Map<String,String> paramsMap = new HashMap<String,String> ();
        String lastKeyVisited;

        public String getValue(String key) {
            return (paramsMap.get (key));
        }

        public void visitStringNode(Text stringNode) {
            paramsMap.put(lastKeyVisited,stringNode.getText());
        }

        public void visitTag(Tag tag) {
            if (tag.getTagName().equals("PARAM")) {
                lastKeyVisited = tag.getAttribute("NAME");
            }
        }
    }
}
