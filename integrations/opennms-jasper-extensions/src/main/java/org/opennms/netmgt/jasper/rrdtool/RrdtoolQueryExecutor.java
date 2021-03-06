/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.jasper.rrdtool;

import java.util.Map;

import net.sf.jasperreports.engine.JRValueParameter;

import org.opennms.netmgt.jasper.analytics.RrdDataSourceFilter;
import org.opennms.netmgt.jasper.jrobin.JRobinQueryExecutor;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRewindableDataSource;

public class RrdtoolQueryExecutor extends JRobinQueryExecutor {

	protected RrdtoolQueryExecutor(JRDataset dataset, Map<String, ? extends JRValueParameter> parametersMap) {
		super(dataset, parametersMap);
	}

	@Override
	public JRRewindableDataSource createDatasource() throws JRException {
        RrdDataSourceFilter dse = new RrdDataSourceFilter(getQueryString());
        try {
            JRRewindableDataSource ds = new RrdtoolXportCmd().executeCommand(dse.getRrdQueryString());
            return dse.filter(ds);
        } catch (Exception e) {
            throw new JRException("Error creating RrdtoolDataSource with command: " + getQueryString(), e);
        }
	}
}
