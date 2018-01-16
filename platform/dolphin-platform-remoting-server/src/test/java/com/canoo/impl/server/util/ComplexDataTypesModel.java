/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.impl.server.util;

import com.canoo.platform.remoting.RemotingBean;
import com.canoo.platform.remoting.Property;

import java.util.Calendar;
import java.util.Date;

@RemotingBean
public class ComplexDataTypesModel {

    public enum EnumValues { VALUE_1, VALUE_2 }

    private Property<Date> dateProperty;

    private Property<Calendar> calendarProperty;

    private Property<EnumValues> enumProperty;

    public Property<Date> getDateProperty() {
        return dateProperty;
    }

    public Property<Calendar> getCalendarProperty() {
        return calendarProperty;
    }

    public Property<EnumValues> getEnumProperty() {
        return enumProperty;
    }
}
