// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Somik Raha
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/test/java/org/htmlparser/tests/tagTests/ScriptTagTest.java $
// $Author: derrickoswald $
// $Date: 2006-09-17 00:44:17 +1000 (Sun, 17 Sep 2006) $
// $Revision: 4 $
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

package org.htmlparser.tests.tagTests;

import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class ScriptTagTest extends ParserTestCase{

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.ScriptTagTest", "ScriptTagTest");
    }

    public ScriptTagTest(String name)
    {
        super(name);
    }

    public void testCreation() throws ParserException
    {
        String testHtml = "<SCRIPT>Script Code</SCRIPT>";
        createParser(testHtml,"http://localhost/index.html");
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
        ScriptTag scriptTag = (ScriptTag)node[0];
        assertEquals("Script Tag Begin",0,scriptTag.getStartPosition ());
        assertEquals("Script Tag End",28,scriptTag.getEndTag ().getEndPosition ());
        assertEquals("Script Tag Code","Script Code",scriptTag.getScriptCode());
    }

    public void testToHTML() throws ParserException {
        createParser("<SCRIPT>document.write(d+\".com\")</SCRIPT>");
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
        // Check the data in the applet tag
        ScriptTag scriptTag = (ScriptTag)node[0];
        assertEquals("Expected Raw String","<SCRIPT>document.write(d+\".com\")</SCRIPT>",scriptTag.toHtml());
    }

    /**
     * Test raw string.
     * Bug check by Wolfgang Germund 2002-06-02
     * Upon parsing :
     * &lt;script language="javascript"&gt;
     * if(navigator.appName.indexOf("Netscape") != -1)
     * document.write ('xxx');
     * else
     * document.write ('yyy');
     * &lt;/script&gt;
     * check toRawString().
     */
    public void testToHTMLWG() throws ParserException
    {
        StringBuffer sb2 = new StringBuffer();
        sb2.append("<script language=\"javascript\">\r\n");
        sb2.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n");
        sb2.append(" document.write ('xxx');\r\n");
        sb2.append("else\r\n");
        sb2.append(" document.write ('yyy');\r\n");
        sb2.append("</script>");
        String expectedHTML = sb2.toString();

        StringBuffer sb1 = new StringBuffer();
        sb1.append("<body>");
        sb1.append(expectedHTML);
        sb1.append("\r\n");
        String testHTML1 = sb1.toString();

        createParser(testHTML1);
        parser.setNodeFactory (new PrototypicalNodeFactory (new ScriptTag ()));
        parseAndAssertNodeCount(3);
        assertTrue("Node should be a script tag",node[1]
        instanceof ScriptTag);
        // Check the data in the script tag
        ScriptTag scriptTag = (ScriptTag)node[1];
        assertStringEquals("Expected Script Code",expectedHTML,scriptTag.toHtml());
    }

    public void testParamExtraction() throws ParserException {
        StringBuffer sb1 = new StringBuffer();
        sb1.append("<script src=\"/adb.js\" language=\"javascript\">\r\n");
        sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n");
        sb1.append(" document.write ('xxx');\r\n");
        sb1.append("else\r\n");
        sb1.append(" document.write ('yyy');\r\n");
        sb1.append("</script>\r\n");
        createParser(sb1.toString());
        parseAndAssertNodeCount(2);
        assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
        ScriptTag scriptTag = (ScriptTag)node[0];
        assertEquals("Script Src","/adb.js",scriptTag.getAttribute("src"));
        assertEquals("Script Language","javascript",scriptTag.getAttribute("language"));
    }

    public void testVariableDeclarations() throws ParserException {
        StringBuffer sb1 = new StringBuffer();
        sb1.append("<script language=\"javascript\">\n");
        sb1.append("var lower = '<%=lowerValue%>';\n");
        sb1.append("</script>\n");
        createParser(sb1.toString());
        parseAndAssertNodeCount(2);
        assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
        ScriptTag scriptTag = (ScriptTag)node[0];
        assertStringEquals("Script toHTML()","<script language=\"javascript\">\nvar lower = '<%=lowerValue%>';\n</script>",scriptTag.toHtml());
    }

    public void testSingleApostropheParsingBug() throws ParserException {
        String script = "<script src='<%=sourceFileName%>'></script>";
        createParser(script);
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
        ScriptTag scriptTag = (ScriptTag)node[0];
        assertStringEquals("Script toHTML()",script,scriptTag.toHtml());
    }

}
