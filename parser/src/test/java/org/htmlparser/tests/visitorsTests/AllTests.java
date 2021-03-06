// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Somik Raha
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/test/java/org/htmlparser/tests/visitorsTests/AllTests.java $
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

package org.htmlparser.tests.visitorsTests;

import junit.framework.TestSuite;
import org.htmlparser.tests.ParserTestCase;

public class AllTests extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.visitorsTests.AllTests", "AllTests");
    }

    public AllTests(String name) {
        super(name);
    }

    public static TestSuite suite() {
        TestSuite suite = new TestSuite("Visitor Tests");

        suite.addTestSuite(CompositeTagFindingVisitorTest.class);
        suite.addTestSuite(HtmlPageTest.class);
        suite.addTestSuite(LinkFindingVisitorTest.class);
        suite.addTestSuite(NodeVisitorTest.class);
        suite.addTestSuite(StringFindingVisitorTest.class);
        suite.addTestSuite(TagFindingVisitorTest.class);
        suite.addTestSuite(TextExtractingVisitorTest.class);
        suite.addTestSuite(UrlModifyingVisitorTest.class);
        suite.addTestSuite(ScriptCommentTest.class);

        return suite;
    }
}
