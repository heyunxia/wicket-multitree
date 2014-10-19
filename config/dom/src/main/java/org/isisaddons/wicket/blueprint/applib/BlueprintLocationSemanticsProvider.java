/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.wicket.blueprint.applib;

import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;
import org.apache.isis.applib.profiles.Localization;

/**
 * For internal use; allows Isis to parse etc.
 */
public class BlueprintLocationSemanticsProvider implements ValueSemanticsProvider<BlueprintLocation> {

	public DefaultsProvider<BlueprintLocation> getDefaultsProvider() {
		return new DefaultsProvider<BlueprintLocation>() {

			public BlueprintLocation getDefaultValue() {
				return BlueprintLocation.DEFAULT_VALUE;
			}
		};
	}

	public EncoderDecoder<BlueprintLocation> getEncoderDecoder() {
		return new EncoderDecoder<BlueprintLocation>() {

			public BlueprintLocation fromEncodedString(String encodedString) {
				return BlueprintLocation.fromString(encodedString);
			}

			public String toEncodedString(BlueprintLocation BlueprintToEncode) {
				return BlueprintToEncode.toString();
			}
		};
	}

	public Parser<BlueprintLocation> getParser() {
		return new Parser<BlueprintLocation>() {

			public String displayTitleOf(BlueprintLocation blueprintLocation, String usingMask) {
				return blueprintLocation.toString();
			}

			public String parseableTitleOf(BlueprintLocation existing) {
				return existing.toString();
			}

			public int typicalLength() {
				return BlueprintLocation.typicalLength();
			}

            public String displayTitleOf(BlueprintLocation blueprintLocation, Localization arg1) {
                return blueprintLocation.toString();
            }

            public BlueprintLocation parseTextEntry(Object object, String entry, Localization arg2) {
                return BlueprintLocation.fromString(entry);
            }
		};
	}

	public boolean isEqualByContent() {
		return true;
	}

	public boolean isImmutable() {
		return true;
	}

}
