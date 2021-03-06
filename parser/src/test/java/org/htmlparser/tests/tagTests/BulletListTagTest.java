// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/test/java/org/htmlparser/tests/tagTests/BulletListTagTest.java $
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

import org.htmlparser.Node;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.Text;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class BulletListTagTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.BulletListTagTest", "BulletListTagTest");
    }

    public BulletListTagTest (String name)
    {
        super(name);
    }
    
    public void testScan() throws ParserException {
        createParser(
            "<ul TYPE=DISC>" +
                "<ul TYPE=\"DISC\"><li>Energy supply\n"+
                    " (Campbell)  <A HREF=\"/hansard/37th3rd/h20307p.htm#1646\">1646</A>\n"+
                    " (MacPhail)  <A HREF=\"/hansard/37th3rd/h20307p.htm#1646\">1646</A>\n"+
                "</ul><A NAME=\"calpinecorp\"></A><B>Calpine Corp.</B>\n"+
                "<ul TYPE=\"DISC\"><li>Power plant projects\n"+
                    " (Neufeld)  <A HREF=\"/hansard/37th3rd/h20314p.htm#1985\">1985</A>\n"+
                "</ul>" +
            "</ul>"
        );
        parseAndAssertNodeCount(1);

        NodeList nestedBulletLists =
            ((CompositeTag)node[0]).searchFor(
                BulletList.class,
                true
            );
        assertEquals(
            "bullets in first list",
            2,
            nestedBulletLists.size()
        );
        BulletList firstList =
            (BulletList)nestedBulletLists.elementAt(0);
        Bullet firstBullet =
            (Bullet)firstList.childAt(0);
        Node firstNodeInFirstBullet =
            firstBullet.childAt(0);
        assertType(
            "first child in bullet",
            Text.class,
            firstNodeInFirstBullet
        );
        assertStringEquals(
            "expected text",
            "Energy supply\n" +
            " (Campbell)  ",
            firstNodeInFirstBullet.toPlainTextString()
        );
    }

    public void testMissingendtag ()
        throws ParserException
    {
        createParser ("<li>item 1<li>item 2");
        parseAndAssertNodeCount (2);
        assertStringEquals ("item 1 not correct", "item 1", ((Bullet)node[0]).childAt (0).toHtml ());
        assertStringEquals ("item 2 not correct", "item 2", ((Bullet)node[1]).childAt (0).toHtml ());
    }
}
