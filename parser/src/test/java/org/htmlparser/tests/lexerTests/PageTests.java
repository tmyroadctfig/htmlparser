// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/test/java/org/htmlparser/tests/lexerTests/PageTests.java $
// $Author: derrickoswald $
// $Date: 2011-04-25 21:17:47 +1000 (Mon, 25 Apr 2011) $
// $Revision: 75 $
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

package org.htmlparser.tests.lexerTests;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.lexer.Page;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class PageTests extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.lexerTests.PageTests", "PageTests");
    }

    /**
     * The default charset.
     * This should be <code>ISO-8859-1</code>,
     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
     * Another alias is "8859_1".
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * Base URI for absolute URL tests.
     */
    static final String BASEURI = "http://a/b/c/d;p?q";

    /**
     * Page for absolute URL tests.
     */
    public static Page mPage;
    static
    {
        mPage = new Page ();
        mPage.setBaseUrl (BASEURI);
    }
        
    /**
     * Test the third level page class.
     */
    public PageTests (String name)
    {
        super (name);
    }

    /**
     * Test initialization with a null value.
     */
    public void testNull () throws ParserException
    {
        try
        {
            new Page ((URLConnection)null);
            assertTrue ("null value in constructor", false);
        }
        catch (IllegalArgumentException iae)
        {
            // expected outcome
        }

        try
        {
            new Page ((String)null);
            assertTrue ("null value in constructor", false);
        }
        catch (IllegalArgumentException iae)
        {
            // expected outcome
        }
    }

    /**
     * Test initialization with a real value.
     */
    public void testURLConnection () throws ParserException, IOException
    {
        String link;
        URL url;

        link = "http://www.ibm.com/jp/";
        url = new URL (link);
        new Page (url.openConnection ());
    }

    /**
     * Test initialization with non-existant URL.
     */
    public void testBadURLConnection () throws IOException
    {
        String link;
        URL url;

        link = "http://www.bigbogosity.org/";
        url = new URL (link);
        try
        {
           new Page (url.openConnection ());
        }
        catch (ParserException pe)
        {
            // expected response
        }
    }

    //
    // Tests from Appendix C Examples of Resolving Relative URI References
    // RFC 2396 Uniform Resource Identifiers (URI): Generic Syntax
    // T. Berners-Lee et al.
    // http://www.ietf.org/rfc/rfc2396.txt

    // Within an object with a well-defined base URI of
    // http://a/b/c/d;p?q
    // the relative URI would be resolved as follows:

    // C.1.  Normal Examples
    //  g:h           =  g:h
    //  g             =  http://a/b/c/g
    //  ./g           =  http://a/b/c/g
    //  g/            =  http://a/b/c/g/
    //  /g            =  http://a/g
    //  //g           =  http://g
    //  ?y            =  http://a/b/c/?y
    //  g?y           =  http://a/b/c/g?y
    //  #s            =  (current document)#s
    //  g#s           =  http://a/b/c/g#s
    //  g?y#s         =  http://a/b/c/g?y#s
    //  ;x            =  http://a/b/c/;x
    //  g;x           =  http://a/b/c/g;x
    //  g;x?y#s       =  http://a/b/c/g;x?y#s
    //  .             =  http://a/b/c/
    //  ./            =  http://a/b/c/
    //  ..            =  http://a/b/
    //  ../           =  http://a/b/
    //  ../g          =  http://a/b/g
    //  ../..         =  http://a/
    //  ../../        =  http://a/
    //  ../../g       =  http://a/g

    public void test1 ()
    {
        assertEquals ("test1 failed", "https:h", mPage.getAbsoluteURL ("https:h"));
    }
    public void test2 ()
    {
        assertEquals ("test2 failed", "http://a/b/c/g", mPage.getAbsoluteURL ("g"));
    }
    public void test3 ()
    {
        assertEquals ("test3 failed", "http://a/b/c/g", mPage.getAbsoluteURL ("./g"));
    }
    public void test4 ()
    {
        assertEquals ("test4 failed", "http://a/b/c/g/", mPage.getAbsoluteURL ("g/"));
    }
    public void test5 ()
    {
        assertEquals ("test5 failed", "http://a/g", mPage.getAbsoluteURL ("/g"));
    }
    public void test6 ()
    {
        assertEquals ("test6 failed", "http://g", mPage.getAbsoluteURL ("//g"));
    }
    public void test7 ()
    {
        assertEquals ("test7 strict failed", "http://a/b/c/?y", mPage.getAbsoluteURL ("?y", true));
        assertEquals ("test7 non-strict failed", "http://a/b/c/d;p?y", mPage.getAbsoluteURL ("?y"));
    }
    public void test8 ()
    {
        assertEquals ("test8 failed", "http://a/b/c/g?y", mPage.getAbsoluteURL ("g?y"));
    }
    public void test9 ()
    {
        assertEquals ("test9 failed", "https:h", mPage.getAbsoluteURL ("https:h"));
    }
    public void test10 ()
    {
        assertEquals ("test10 failed", "https:h", mPage.getAbsoluteURL ("https:h"));
    }
    //  #s            =  (current document)#s
    public void test11 ()
    {
        assertEquals ("test11 failed", "http://a/b/c/g#s", mPage.getAbsoluteURL ("g#s"));
    }
    public void test12 ()
    {
        assertEquals ("test12 failed", "http://a/b/c/g?y#s", mPage.getAbsoluteURL ("g?y#s"));
    }
    public void test13 ()
    {
        assertEquals ("test13 failed", "http://a/b/c/;x", mPage.getAbsoluteURL (";x"));
    }
    public void test14 ()
    {
        assertEquals ("test14 failed", "http://a/b/c/g;x", mPage.getAbsoluteURL ("g;x"));
    }
    public void test15 ()
    {
        assertEquals ("test15 failed", "http://a/b/c/g;x?y#s", mPage.getAbsoluteURL ("g;x?y#s"));
    }
    public void test16 ()
    {
        assertEquals ("test16 failed", "http://a/b/c/", mPage.getAbsoluteURL ("."));
    }
    public void test17 ()
    {
        assertEquals ("test17 failed", "http://a/b/c/", mPage.getAbsoluteURL ("./"));
    }
    public void test18 ()
    {
        assertEquals ("test18 failed", "http://a/b/", mPage.getAbsoluteURL (".."));
    }
    public void test19 ()
    {
        assertEquals ("test19 failed", "http://a/b/", mPage.getAbsoluteURL ("../"));
    }
    public void test20 ()
    {
        assertEquals ("test20 failed", "http://a/b/g", mPage.getAbsoluteURL ("../g"));
    }
    public void test21 ()
    {
        assertEquals ("test21 failed", "http://a/", mPage.getAbsoluteURL ("../.."));
    }
    public void test22 ()
    {
        assertEquals ("test22 failed", "http://a/g", mPage.getAbsoluteURL ("../../g"));
    }

    // C.2.  Abnormal Examples
    //   Although the following abnormal examples are unlikely to occur in
    //   normal practice, all URI parsers should be capable of resolving them
    //   consistently.  Each example uses the same base as above.
    //
    //   An empty reference refers to the start of the current document.
    //
    //      <>            =  (current document)
    //
    //   Parsers must be careful in handling the case where there are more
    //   relative path ".." segments than there are hierarchical levels in the
    //   base URI's path.  Note that the ".." syntax cannot be used to change
    //   the authority component of a URI.
    //
    //      ../../../g    =  http://a/../g
    //      ../../../../g =  http://a/../../g
    //
    //   In practice, some implementations strip leading relative symbolic
    //   elements (".", "..") after applying a relative URI calculation, based
    //   on the theory that compensating for obvious author errors is better
    //   than allowing the request to fail.  Thus, the above two references
    //   will be interpreted as "http://a/g" by some implementations.
    //
    //   Similarly, parsers must avoid treating "." and ".." as special when
    //   they are not complete components of a relative path.
    //
    //      /./g          =  http://a/./g
    //      /../g         =  http://a/../g
    //      g.            =  http://a/b/c/g.
    //      .g            =  http://a/b/c/.g
    //      g..           =  http://a/b/c/g..
    //      ..g           =  http://a/b/c/..g
    //
    //   Less likely are cases where the relative URI uses unnecessary or
    //   nonsensical forms of the "." and ".." complete path segments.
    //
    //      ./../g        =  http://a/b/g
    //      ./g/.         =  http://a/b/c/g/
    //      g/./h         =  http://a/b/c/g/h
    //      g/../h        =  http://a/b/c/h
    //      g;x=1/./y     =  http://a/b/c/g;x=1/y
    //      g;x=1/../y    =  http://a/b/c/y
    //
    //   All client applications remove the query component from the base URI
    //   before resolving relative URI.  However, some applications fail to
    //   separate the reference's query and/or fragment components from a
    //   relative path before merging it with the base path.  This error is
    //   rarely noticed, since typical usage of a fragment never includes the
    //   hierarchy ("/") character, and the query component is not normally
    //   used within relative references.
    //
    //      g?y/./x       =  http://a/b/c/g?y/./x
    //      g?y/../x      =  http://a/b/c/g?y/../x
    //      g#s/./x       =  http://a/b/c/g#s/./x
    //      g#s/../x      =  http://a/b/c/g#s/../x
    //
    //   Some parsers allow the scheme name to be present in a relative URI if
    //   it is the same as the base URI scheme.  This is considered to be a
    //   loophole in prior specifications of partial URI [RFC1630]. Its use
    //   should be avoided.
    //
    //      http:g        =  http:g           ; for validating parsers
    //                    |  http://a/b/c/g   ; for backwards compatibility

//    public void test23 () throws HTMLParserException
//    {
//        assertEquals ("test23 failed", "http://a/../g", mPage.getAbsoluteURL ("../../../g"));
//    }
//    public void test24 () throws HTMLParserException
//    {
//        assertEquals ("test24 failed", "http://a/../../g", mPage.getAbsoluteURL ("../../../../g"));
//    }
    public void test23 ()
    {
        assertEquals ("test23 failed", "http://a/g", mPage.getAbsoluteURL ("../../../g"));
    }
    public void test24 ()
    {
        assertEquals ("test24 failed", "http://a/g", mPage.getAbsoluteURL ("../../../../g"));
    }
    public void test25 ()
    {
        assertEquals ("test25 failed", "http://a/./g", mPage.getAbsoluteURL ("/./g"));
    }
    public void test26 ()
    {
        assertEquals ("test26 failed", "http://a/../g", mPage.getAbsoluteURL ("/../g"));
    }
    public void test27 ()
    {
        assertEquals ("test27 failed", "http://a/b/c/g.", mPage.getAbsoluteURL ("g."));
    }
    public void test28 ()
    {
        assertEquals ("test28 failed", "http://a/b/c/.g", mPage.getAbsoluteURL (".g"));
    }
    public void test29 ()
    {
        assertEquals ("test29 failed", "http://a/b/c/g..", mPage.getAbsoluteURL ("g.."));
    }
    public void test30 ()
    {
        assertEquals ("test30 failed", "http://a/b/c/..g", mPage.getAbsoluteURL ("..g"));
    }
    public void test31 ()
    {
        assertEquals ("test31 failed", "http://a/b/g", mPage.getAbsoluteURL ("./../g"));
    }
    public void test32 ()
    {
        assertEquals ("test32 failed", "http://a/b/c/g/", mPage.getAbsoluteURL ("./g/."));
    }
    public void test33 ()
    {
        assertEquals ("test33 failed", "http://a/b/c/g/h", mPage.getAbsoluteURL ("g/./h"));
    }
    public void test34 ()
    {
        assertEquals ("test34 failed", "http://a/b/c/h", mPage.getAbsoluteURL ("g/../h"));
    }
    public void test35 ()
    {
        assertEquals ("test35 failed", "http://a/b/c/g;x=1/y", mPage.getAbsoluteURL ("g;x=1/./y"));
    }
    public void test36 ()
    {
        assertEquals ("test36 failed", "http://a/b/c/y", mPage.getAbsoluteURL ("g;x=1/../y"));
    }
    public void test37 ()
    {
        assertEquals ("test37 failed", "http://a/b/c/g?y/./x", mPage.getAbsoluteURL ("g?y/./x"));
    }
    public void test38 ()
    {
        assertEquals ("test38 failed", "http://a/b/c/g?y/../x", mPage.getAbsoluteURL ("g?y/../x"));
    }
    public void test39 ()
    {
        assertEquals ("test39 failed", "http://a/b/c/g#s/./x", mPage.getAbsoluteURL ("g#s/./x"));
    }
    public void test40 ()
    {
        assertEquals ("test40 failed", "http://a/b/c/g#s/../x", mPage.getAbsoluteURL ("g#s/../x"));
    }
//    public void test41 ()
//    {
//        assertEquals ("test41 failed", "http:g", mPage.getAbsoluteURL ("http:g"));
//    }
    public void test41 ()
    {
        assertEquals ("test41 failed", "http://a/b/c/g", mPage.getAbsoluteURL ("http:g"));
    }

}
