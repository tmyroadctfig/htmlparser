// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/filterbuilder/src/main/java/org/htmlparser/parserapplications/filterbuilder/wrappers/HasParentFilterWrapper.java $
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.parserapplications.filterbuilder.SubFilterList;

/**
 * Wrapper for HasParentFilters.
 */
public class HasParentFilterWrapper
    extends
        Filter
    implements
        ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The underlying filter.
     */
    protected HasParentFilter mFilter;

    /**
     * The check box for recursion.
     */
    protected JCheckBox mRecursive;

    /**
     * The drop target container.
     */
    protected SubFilterList mContainer;
    
    /**
     * Create a wrapper over a new HasParentFilter.
     */ 
    public HasParentFilterWrapper ()
    {
        mFilter = new HasParentFilter ();

        // add the recursive flag
        mRecursive = new JCheckBox ("Recursive");
        add (mRecursive);
        mRecursive.addActionListener (this);
        mRecursive.setSelected (mFilter.getRecursive ());

        // add the subfilter container
        mContainer = new SubFilterList (this, "Parent Filter", 1);
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
        return ("Has Parent");
    }

    /**
     * Get the resource name for the icon.
     * @return The icon resource specification.
     */
    public String getIconSpec ()
    {
        return ("images/HasParentFilter.gif");
    }

    /**
     * Get the underlying node filter object.
     * @return The node filter object suitable for serialization.
     */
    public NodeFilter getNodeFilter ()
    {
        NodeFilter filter;
        HasParentFilter ret;
        
        ret = new HasParentFilter ();

        ret.setRecursive (mFilter.getRecursive ());
        filter = mFilter.getParentFilter ();
        if (null != filter)
            ret.setParentFilter (((Filter)filter).getNodeFilter ());
            
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
        mFilter = (HasParentFilter)filter;
        mRecursive.setSelected (mFilter.getRecursive ());
    }

    /**
     * Get the underlying node filter's subordinate filters.
     * @return The node filter object's contained filters.
     */
    public NodeFilter[] getSubNodeFilters ()
    {
        NodeFilter filter;
        NodeFilter[] ret;

        filter = mFilter.getParentFilter ();
        if (null != filter)
            ret = new NodeFilter[] { filter };
        else
            ret = new NodeFilter[0];

        return (ret);
    }

    /**
     * Assign the underlying node filter's subordinate filters.
     * @param filters The filters to insert into the underlying node filter.
     */
    public void setSubNodeFilters (NodeFilter[] filters)
    {
        if (0 != filters.length)
            mFilter.setParentFilter (filters[0]);
        else
            mFilter.setParentFilter (null);
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
        String name;
        String ret;

        if (null != mFilter.getParentFilter ())
            name = ((Filter)mFilter.getParentFilter ()).toJavaCode (out, context);
        else
            name = null;
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("HasParentFilter ");
        out.append (ret);
        out.append (" = new HasParentFilter ();");
        newline (out);
        spaces (out, context[0]);
        out.append (ret);
        out.append (".setRecursive (");
        out.append (mFilter.getRecursive () ? "true" : "false");
        out.append (");");
        newline (out);
        if (null != name)
        {
            spaces (out, context[0]);
            out.append (ret);
            out.append (".setParentFilter (");
            out.append (name);
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

    //
    // ActionListener interface
    //

    /**
     * Invoked when an action occurs on the check box.
     * @param event Details about the action event.
     */
    public void actionPerformed (ActionEvent event)
    {
        Object source;
        boolean recursive;

        source = event.getSource ();
        if (source == mRecursive)
        {
            recursive = mRecursive.isSelected ();
            mFilter.setRecursive (recursive);
        }
    }
}
