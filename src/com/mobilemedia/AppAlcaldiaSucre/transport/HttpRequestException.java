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
 * @author kseo
 *
 */
public class HttpRequestException extends Exception
{
    /**
    *
    */
    private static final long serialVersionUID = -2640438334482433748L;

    public HttpRequestException()
    {
        super();
    }

    public HttpRequestException(String message)
    {
        super(message);
    }

}
