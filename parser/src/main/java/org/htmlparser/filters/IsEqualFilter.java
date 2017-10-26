// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/main/java/org/htmlparser/filters/IsEqualFilter.java $
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

package org.htmlparser.filters;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

/**
 * This class accepts only one specific node.
 */
public class IsEqualFilter implements NodeFilter
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The node to match.
     */
    protected Node mNode;

    /**
     * Creates a new IsEqualFilter that accepts only the node provided.
     * @param node The node to match.
     */
    public IsEqualFilter (Node node)
    {
        mNode = node;
    }

    /**
     * Accept the node.
     * @param node The node to check.
     * @return <code>false</code> unless <code>node</code> is the one and only.
     */
    public boolean accept (Node node)
    {
        return (mNode == node);
    }
}
