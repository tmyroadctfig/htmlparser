// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/filterbuilder/src/main/java/org/htmlparser/parserapplications/filterbuilder/wrappers/OrFilterWrapper.java $
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

package org.htmlparser.parserapplications.filterbuilder.wrappers;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.parserapplications.filterbuilder.SubFilterList;

/**
 * Wrapper for OrFilters.
 */
public class OrFilterWrapper
    extends
        Filter
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The drop target container.
     */
    protected SubFilterList mContainer;
    
    /**
     * The underlying filter.
     */
    protected OrFilter mFilter;

    /**
     * Create a wrapper over a new OrFilter.
     */ 
    public OrFilterWrapper ()
    {
        mFilter = new OrFilter ();

        // add the subfilter container
        mContainer = new SubFilterList (this, "Predicates", 0);
        add (mContainer);
    }

    //
    // Filter overrides and concrete implementations
    //

    /**
     * Get the name of the filter.
     * @return A descriptive name for the filter.
     */
    public String getDescription ()
    {
        return ("Or");
    }

    /**
     * Get the resource name for the icon.
     * @return The icon resource specification.
     */
    public String getIconSpec ()
    {
        return ("images/OrFilter.gif");
    }

    /**
     * Get the underlying node filter object.
     * @return The node filter object suitable for serialization.
     */
    public NodeFilter getNodeFilter ()
    {
        NodeFilter[] predicates;
        NodeFilter[] temp;
        OrFilter ret;
        
        ret = new OrFilter ();

        predicates = mFilter.getPredicates ();
        temp = new NodeFilter[predicates.length];
        for (int i = 0; i < predicates.length; i++)
            temp[i] = ((Filter)predicates[i]).getNodeFilter ();
        ret.setPredicates (temp);
            
        return (ret);
    }

    /**
     * Assign the underlying node filter for this wrapper.
     * @param filter The filter to wrap.
     * @param context The parser to use for conditioning this filter.
     * Some filters need contextual information to provide to the user,
     * i.e. for tag names or attribute names or values,
     * so the Parser context is provided. 
     */
    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        mFilter = (OrFilter)filter;
    }

    /**
     * Get the underlying node filter's subordinate filters.
     * @return The node filter object's contained filters.
     */
    public NodeFilter[] getSubNodeFilters ()
    {
        return (mFilter.getPredicates ());
    }

    /**
     * Assign the underlying node filter's subordinate filters.
     * @param filters The filters to insert into the underlying node filter.
     */
    public void setSubNodeFilters (NodeFilter[] filters)
    {
        mFilter.setPredicates (filters);
    }

    /**
     * Convert this filter into Java code.
     * Output whatever text necessary and return the variable name.
     * @param out The output buffer.
     * @param context Three integers as follows:
     * <li>indent level - the number of spaces to insert at the beginning of each line</li>
     * <li>filter number - the next available filter number</li>
     * <li>filter array number - the next available array of filters number</li>
     * @return The variable name to use when referencing this filter (usually "filter" + context[1]++) 
     */
    public String toJavaCode (StringBuffer out, int[] context)
    {
        String array;
        NodeFilter[] predicates;
        String[] names;
        String ret;
        
        predicates = mFilter.getPredicates ();
        array = null; // stoopid Java compiler
        if (0 != predicates.length)
        {
            names = new String[predicates.length];
            for (int i = 0; i < predicates.length; i++)
            {
                names[i] = ((Filter)predicates[i]).toJavaCode (out, context);
            }
            array = "array" + context[2]++;
            spaces (out, context[0]);
            out.append ("NodeFilter[] ");
            out.append (array);
            out.append (" = new NodeFilter[");
            out.append (predicates.length);
            out.append ("];");
            newline (out);
            for (int i = 0; i < predicates.length; i++)
            {
                spaces (out, context[0]);
                out.append (array);
                out.append ("[");
                out.append (i);
                out.append ("] = ");
                out.append (names[i]);
                out.append (";");
                newline (out);
            }
        }
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("OrFilter ");
        out.append (ret);
        out.append (" = new OrFilter ();");
        newline (out);
        if (0 != predicates.length)
        {
            spaces (out, context[0]);
            out.append (ret);
            out.append (".setPredicates (");
            out.append (array);
            out.append (");");
            newline (out);
        }
        
        return (ret);
    }

    //
    // NodeFilter interface
    //

    /**
     * Predicate to determine whether or not to keep the given node.
     * The behaviour based on this outcome is determined by the context
     * in which it is called. It may lead to the node being added to a list
     * or printed out. See the calling routine for details.
     * @return <code>true</code> if the node is to be kept, <code>false</code>
     * if it is to be discarded.
     * @param node The node to test.
     */
    public boolean accept (Node node)
    {
        return (mFilter.accept (node));
    }
}
