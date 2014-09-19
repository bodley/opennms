/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.config.datacollection;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;
import org.opennms.core.test.xml.XmlTest;
import org.opennms.netmgt.config.datacollection.PersistenceSelectorStrategy;
import org.opennms.netmgt.config.datacollection.ResourceType;
import org.opennms.netmgt.config.datacollection.StorageStrategy;

public class ResourceTypeTest extends XmlTest<ResourceType> {

    public ResourceTypeTest(final ResourceType sampleObject, final String sampleXml, final String schemaFile) {
        super(sampleObject, sampleXml, schemaFile);
    }
    
    @Parameters
    public static Collection<Object[]> data() throws ParseException {
        final ResourceType type = new ResourceType();
        type.setName("rbshCpuIndivIndex");
        type.setLabel("Riverbed Steelhead CPU");
        type.setResourceLabel("CPU ${rbshCpuIndivId}");
        type.setPersistenceSelectorStrategy(new PersistenceSelectorStrategy("org.opennms.netmgt.collectd.PersistAllSelectorStrategy"));
        type.setStorageStrategy(new StorageStrategy("org.opennms.netmgt.dao.support.IndexStorageStrategy"));

        return Arrays.asList(new Object[][] { {
                type,
                "    <resourceType name=\"rbshCpuIndivIndex\" label=\"Riverbed Steelhead CPU\" resourceLabel=\"CPU ${rbshCpuIndivId}\">\n" + 
                "      <persistenceSelectorStrategy class=\"org.opennms.netmgt.collectd.PersistAllSelectorStrategy\"/>\n" + 
                "      <storageStrategy class=\"org.opennms.netmgt.dao.support.IndexStorageStrategy\"/>\n" + 
                "    </resourceType>\n",
                "target/classes/xsds/datacollection-config.xsd" } });
    }


}