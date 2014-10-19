/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.isisaddons.wicket.blueprint.applib;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

import org.apache.isis.applib.annotation.Value;

/**
 * Value type representing a location on a floor plan.
 * 
 */
@Value(semanticsProviderClass = BlueprintLocationSemanticsProvider.class)
public class BlueprintLocation implements Serializable {
    private static final long serialVersionUID = 1L;

    static final BlueprintLocation DEFAULT_VALUE = new BlueprintLocation(0, 0);

    /**
     * 
     */
    public BlueprintLocation() {
        this(DEFAULT_VALUE.x, DEFAULT_VALUE.y);
    }

    public BlueprintLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int x;
    private int y;


    /**
     * See {@link #fromString(String)}
     */
    @Override
    public String toString() {
        return String.format("%d;%d", x, y);
    }

    public static int typicalLength() {
        // x and y are each up to -NNNN,NNNN (8 chars); plus 1 for the ';'
        return 8*2+1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

	//{{ Encoder/decoder helpers
	public static BlueprintLocation fromString(String encodedString) {
	    if(encodedString == null) {
	        return null;
	    }
	    final String[] split = encodedString.split(";");
	    if(split.length != 2) {
	        return null;
	    }
	    for (Locale locale : Arrays.asList(Locale.getDefault(), Locale.ENGLISH, Locale.FRENCH, Locale.JAPANESE, Locale.CHINESE)) {
	        try {
	            String latStr = split[0];
	            String longStr = split[1];

	            return parse(locale, latStr, longStr);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            continue;
	        }
        }
	    return null;
	}

    private static BlueprintLocation parse(Locale locale, String latStr, String longStr) throws ParseException {
        NumberFormat nf = getNumberFormat(locale);
        int xPos = ((Long) nf.parse(latStr)).intValue();
        int yPos = ((Long) nf.parse(longStr)).intValue();
        return new BlueprintLocation(xPos, yPos);
    }

    private static NumberFormat getNumberFormat(Locale locale) {
        NumberFormat nf = NumberFormat.getInstance(locale);
        return nf;
    }
    

	//}} Encoder/decoder helpers
    
}
