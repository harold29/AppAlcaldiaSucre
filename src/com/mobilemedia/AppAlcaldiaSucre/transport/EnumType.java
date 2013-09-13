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
 * A basic implementation of integer based enum functionality
 *
 * @author kseo
 */
public class EnumType {
    private final int value;
    private String description = null;

    protected EnumType(int value) {
        this(value, null);
    }

    protected EnumType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;

        EnumType type = (EnumType) obj;
        // return (value == type.value || (value != null &&
        // value.equals(type.value)));
        return (value == type.value);
    }

    /**
     * @return the int enum value
     */
    public final int value() {
        return value;
    }

    /**
     * @return the enum value description
     */
    public String description() {
        if (description != null)
            return description;
        else
            return "No Description Available";
    }

    public int hashCode() {
        int hash = 7;
        // hash = 31 * hash + (null == value ? 0 : value.hashCode());
        hash = 31 * hash + value;
        return hash;
    }

}
