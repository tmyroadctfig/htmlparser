// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/parser/src/main/java/org/htmlparser/filters/NotFilter.java $
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
 * Accepts all nodes not acceptable to it's predicate filter.
 */
public class NotFilter implements NodeFilter
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The filter to gainsay.
     */
    protected NodeFilter mPredicate;

    /**
     * Creates a new instance of a NotFilter.
     * With no predicates, this would always return <code>false</code>
     * from {@link #accept}.
     * @see #setPredicate
     */
    public NotFilter ()
    {
        setPredicate (null);
    }

    /**
     * Creates a NotFilter that accepts nodes not acceptable to the predicate.
     * @param predicate The filter to consult.
     */
    public NotFilter (NodeFilter predicate)
    {
        setPredicate (predicate);
    }

    /**
     * Get the predicate used by this NotFilter.
     * @return The predicate currently in use.
     */
    public NodeFilter getPredicate ()
    {
        return (mPredicate);
    }

    /**
     * Set the predicate for this NotFilter.
     * @param predicate The predidcate to use in {@link #accept}.
     */
    public void setPredicate (NodeFilter predicate)
    {
        mPredicate = predicate;
    }

    //
    // NodeFilter interface
    //

    /**
     * Accept nodes that are not acceptable to the predicate filter.
     * @param node The node to check.
     * @return <code>true</code> if the node is not acceptable to the
     * predicate filter, <code>false</code> otherwise.
     */
    public boolean accept (Node node)
    {
        return ((null != mPredicate) && !mPredicate.accept (node));
    }
}
