/**
 * Copyright © 1998-2009 Research In Motion Ltd.
 *
 * Note:
 *
 * 1. For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 *
 * 2. The sample serves as a demonstration of principles and is not intended for a
 * full featured application. It makes no guarantees for completeness and is left to
 * the user to use it as sample ONLY.
 */
package com.mobilemedia.AppAlcaldiaSucre.transport;



/**
 * Define all the request status as EnumType
 * @author kseo
 *
 */
public class BBRequestStatus extends EnumType
{
	public final static BBRequestStatus INITIALIZED = new BBRequestStatus(3, "Initialized");
    public final static BBRequestStatus STARTED = new BBRequestStatus(3, "Started");
    public final static BBRequestStatus SUCCEEDED = new BBRequestStatus(4, "Succeeded");
    public final static BBRequestStatus FAILED = new BBRequestStatus(5, "Failed");
    public final static BBRequestStatus CANCELLED = new BBRequestStatus(6, "Cancelled");

    protected BBRequestStatus(int value)
    {
        super(value);
    }

    protected BBRequestStatus(int value, String description)
    {
        super(value, description);
    }

}
