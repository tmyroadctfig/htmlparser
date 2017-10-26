// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://svn.code.sf.net/p/htmlparser/code/trunk/thumbelina/src/main/java/org/htmlparser/lexerapplications/thumbelina/PictureListener.java $
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

package org.htmlparser.lexerapplications.thumbelina;

/**
 * Provides notification functionality for pictures.
 */
public interface PictureListener
{
    /**
     * Notification for a download completed.
     * @param picture The picture for which download has finished.
     */
    void pictureReceived (Picture picture);
    
    /**
     * Notification for a picture ready for use (loaded into memory).
     */
    void pictureReady (Picture picture);
}
