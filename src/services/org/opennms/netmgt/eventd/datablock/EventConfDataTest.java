//
//$Id$
//

package org.opennms.netmgt.eventd.datablock;

import java.io.StringReader;

import junit.framework.TestCase;

import org.opennms.netmgt.eventd.EventConfigurationManager;
import org.opennms.netmgt.mock.EventWrapper;
import org.opennms.netmgt.mock.EventConfWrapper;
import org.opennms.netmgt.mock.MockLogAppender;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Snmp;
import org.opennms.netmgt.xml.eventconf.Logmsg;

public class EventConfDataTest extends TestCase {
	
	/*
	 public static TestSuite suite() {
	 Class testClass = TrapHandlerTest.class;
	 TestSuite suite = new TestSuite(testClass.getName());
	 suite.addTest(new PropertySettingTestSuite(testClass, "JoeSnmp Tests", "org.opennms.snmp.strategyClass", "org.opennms.netmgt.snmp.joesnmp.JoeSnmpStrategy"));
	 suite.addTest(new PropertySettingTestSuite(testClass, "Snmp4J Tests", "org.opennms.snmp.strategyClass", "org.opennms.netmgt.snmp.snmp4j.Snmp4JStrategy"));
	 return suite;
	 }
	 */
	
	protected void setUp() throws Exception {
		MockLogAppender.setupLogging(false);
		
		String eventconf =
			"<events xmlns=\"http://xmlns.opennms.org/xsd/eventconf\">\n" +
			" <global>\n" +
			"  <security>\n" +
			"   <doNotOverride>logmsg</doNotOverride>\n" +
			"   <doNotOverride>operaction</doNotOverride>\n" +
			"   <doNotOverride>autoaction</doNotOverride>\n" +
			"   <doNotOverride>tticket</doNotOverride>\n" +
			"   <doNotOverride>script</doNotOverride>\n" +
			"  </security>\n" +
			" </global>\n" +
			"\n" +
			" <event>\n" +
			"  <mask>\n" +
			"   <maskelement>\n" +
			"    <mename>id</mename>\n" +
			"    <mevalue>.1.3.6.1.2.1.15.7</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>generic</mename>\n" +
			"    <mevalue>6</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>specific</mename>\n" +
			"    <mevalue>1</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>snmphost</mename>\n" +
			"    <mevalue>192.168.1.%</mevalue>\n" +
			"   </maskelement>\n" +
			"  </mask>\n" +
			"  <uei>uei.opennms.org/IETF/BGP/traps/bgpEstablished</uei>\n" +
			"  <event-label>BGP4-MIB defined trap event: bgpEstablished</event-label>\n" +
			"  <descr>&lt;p&gt;The BGP Established event is generated when\n" +
			"   the BGP FSM enters the ESTABLISHED state.&lt;/p&gt;&lt;table&gt;\n" +
			"   &lt;tr&gt;&lt;td&gt;&lt;b&gt;\n" +
			"   bgpPeerLastError&lt;/b&gt;&lt;/td&gt;&lt;td&gt;%parm[#1]%\n" +
			"   &lt;/td&gt;&lt;td&gt;&lt;p;&gt;&lt;/p&gt;&lt;/td;&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;\n" +
			"   bgpPeerState&lt;/b&gt;&lt;/td&gt;&lt;td&gt;%parm[#2]%\n" +
			"   &lt;/td&gt;&lt;td&gt;&lt;p;&gt;\n" +
			"   idle(1) connect(2) active(3) opensent(4) openconfirm(5) established(6)&lt;/p&gt;\n" +
			"   &lt;/td;&gt;&lt;/tr&gt;&lt;/table&gt;\n" +
			"  </descr>\n" +
			"  <logmsg dest='discardtraps'>&lt;p&gt;BGP Event: FSM entered connected state.&lt;/p&gt;</logmsg>\n" +
			"  <severity>Normal</severity>\n" +
			" </event>\n" +
			"\n" +
			" <event>\n" +
			"  <mask>\n" +
			"   <maskelement>\n" +
			"    <mename>id</mename>\n" +
			"    <mevalue>.1.3.6.1.2.1.15.7</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>generic</mename>\n" +
			"    <mevalue>6</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>specific</mename>\n" +
			"    <mevalue>1</mevalue>\n" +
			"   </maskelement>\n" +
			"  </mask>\n" +
			"  <uei>uei.opennms.org/IETF/BGP/traps/bgpEstablished</uei>\n" +
			"  <event-label>BGP4-MIB defined trap event: bgpEstablished</event-label>\n" +
			"  <descr>&lt;p&gt;The BGP Established event is generated when\n" +
			"   the BGP FSM enters the ESTABLISHED state.&lt;/p&gt;&lt;table&gt;\n" +
			"   &lt;tr&gt;&lt;td&gt;&lt;b&gt;\n" +
			"   bgpPeerLastError&lt;/b&gt;&lt;/td&gt;&lt;td&gt;%parm[#1]%\n" +
			"   &lt;/td&gt;&lt;td&gt;&lt;p;&gt;&lt;/p&gt;&lt;/td;&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;\n" +
			"   bgpPeerState&lt;/b&gt;&lt;/td&gt;&lt;td&gt;%parm[#2]%\n" +
			"   &lt;/td&gt;&lt;td&gt;&lt;p;&gt;\n" +
			"   idle(1) connect(2) active(3) opensent(4) openconfirm(5) established(6)&lt;/p&gt;\n" +
			"   &lt;/td;&gt;&lt;/tr&gt;&lt;/table&gt;\n" +
			"  </descr>\n" +
			"  <logmsg dest='logndisplay'>&lt;p&gt;BGP Event: FSM entered connected state.&lt;/p&gt;</logmsg>\n" +
			"  <severity>Normal</severity>\n" +
			" </event>\n" +
			"\n" +
			" <event>\n" +
			"  <mask>\n" +
			"   <maskelement>\n" +
			"    <mename>id</mename>\n" +
			"    <mevalue>.1.3.6.1.2.1.15.7</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>generic</mename>\n" +
			"    <mevalue>6</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>specific</mename>\n" +
			"    <mevalue>2</mevalue>\n" +
			"   </maskelement>\n" +
			"  </mask>\n" +
			"  <uei>uei.opennms.org/IETF/BGP/traps/bgpBackwardTransition</uei>\n" +
			"  <event-label>BGP4-MIB defined trap event: bgpBackwardTransition</event-label>\n" +
			"  <descr>&lt;p&gt;The BGPBackwardTransition Event is generated\n" +
			"   when the BGP FSM moves from a higher numbered\n" +
			"   state to a lower numbered state.&lt;/p&gt;&lt;table&gt;\n" +
			"   &lt;tr&gt;&lt;td&gt;&lt;b&gt;\n" +
			"   bgpPeerLastError&lt;/b&gt;&lt;/td&gt;&lt;td&gt;%parm[#1]%\n" + 
			"   &lt;/td&gt;&lt;td&gt;&lt;p;&gt;&lt;/p&gt;&lt;/td;&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;\n" +
			"   bgpPeerState&lt;/b&gt;&lt;/td&gt;&lt;td&gt;%parm[#2]%\n" + 
			"   &lt;/td&gt;&lt;td&gt;&lt;p;&gt;\n" + 
			"   idle(1) connect(2) active(3) opensent(4) openconfirm(5) established(6)&lt;/p&gt;\n" +
			"   &lt;/td;&gt;&lt;/tr&gt;&lt;/table&gt;\n" +
			"  </descr>\n" + 
			"  <logmsg dest='discardtraps'>&lt;p&gt;BGP Event: FSM Backward Transistion.&lt;/p&gt;</logmsg>\n" + 
			"  <severity>Warning</severity>\n" +
			" </event>\n" +
			"\n" +
			" <event>\n" +
			"  <mask>\n" +
			"   <maskelement>\n" +
			"    <mename>generic</mename>\n" +
			"    <mevalue>0</mevalue>\n" +
			"   </maskelement>\n" +
			"   <maskelement>\n" +
			"    <mename>snmphost</mename>\n" +
			"    <mevalue>192.168.1.1</mevalue>\n" +
			"   </maskelement>\n" +
			"  </mask>\n" +
			"  <uei>uei.opennms.org/generic/traps/SNMP_Cold_Start</uei>\n" +
			"  <event-label>OpenNMS-defined trap event: SNMP_Cold_Start</event-label>\n" +
			"  <descr>\n" +
			"	&lt;p&gt;A coldStart trap signifies that the sending\n" +
			"	protocol entity is reinitializing itself such that the\n" +
			"	agent's configuration or the protocol entity implementation\n" +
			"	may be altered.&lt;/p&gt;\n" +
			"  </descr>\n" +
			"  <logmsg dest='discardtraps'>\n" +
			"	Agent Up with Possible Changes (coldStart Trap)\n" +
			"	enterprise:%id% (%id%) args(%parm[##]%):%parm[all]%\n" +
			"  </logmsg>\n" +
			"  <severity>Normal</severity>\n" +
			" </event>\n" +
			"\n" +
			" <event>\n" +
			"  <mask>\n" +
			"   <maskelement>\n" +
			"    <mename>generic</mename>\n" +
			"    <mevalue>0</mevalue>\n" +
			"   </maskelement>\n" +
			"  </mask>\n" +
			"  <uei>uei.opennms.org/generic/traps/SNMP_Cold_Start</uei>\n" +
			"  <event-label>OpenNMS-defined trap event: SNMP_Cold_Start</event-label>\n" +
			"  <descr>\n" +
			"	&lt;p&gt;A coldStart trap signifies that the sending\n" +
			"	protocol entity is reinitializing itself such that the\n" +
			"	agent's configuration or the protocol entity implementation\n" +
			"	may be altered.&lt;/p&gt;\n" +
			"  </descr>\n" +
			"  <logmsg dest='logndisplay'>\n" +
			"	Agent Up with Possible Changes (coldStart Trap)\n" +
			"	enterprise:%id% (%id%) args(%parm[##]%):%parm[all]%\n" +
			"  </logmsg>\n" +
			"  <severity>Normal</severity>\n" +
			" </event>\n" +
			"</events>";
		
		StringReader reader = new StringReader(eventconf);
		
		EventConfigurationManager.loadConfiguration(reader);
	}
	
	
	public void finishUp() {
	}
	
	public void tearDown() {
		MockLogAppender.assertNoWarningsOrGreater();
	}
	

	
	public void testV1TrapNewSuspect() throws Exception {
		anticipateAndSend(null, "v1", null, 6, 1);
	}
	
	public void testV2TrapNewSuspect() throws Exception {
		anticipateAndSend(null, "v2c", null, 6, 1);
	}
	
	public void testV1EnterpriseIdAndGenericMatch() throws Exception {
		anticipateAndSend("uei.opennms.org/IETF/BGP/traps/bgpEstablished", "v1",
				".1.3.6.1.2.1.15.7", 6, 1);
	}
	
	public void testV2EnterpriseIdAndGenericAndSpecificMatch() throws Exception {
		anticipateAndSend("uei.opennms.org/IETF/BGP/traps/bgpEstablished", "v2c",
				".1.3.6.1.2.1.15.7", 6, 1);
	}

	// This test does not work because the extra zero is pulled off by trapd
	/*
	public void testV2EnterpriseIdAndGenericAndSpecificMatchWithZero() throws Exception {
		anticipateAndSend("uei.opennms.org/IETF/BGP/traps/bgpEstablished", "v2c",
				".1.3.6.1.2.1.15.7.0", 6, 1);
	}
	*/
	
	public void testV2EnterpriseIdAndGenericAndSpecificMissWithExtraZeros() throws Exception {
		anticipateAndSend(null, "v2c",
				".1.3.6.1.2.1.15.7.0.0", 6, 1);
	}
	
	public void testV1EnterpriseIdAndGenericAndSpecificMissWithWrongGeneric() throws Exception {
		anticipateAndSend(null, "v1",
				".1.3.6.1.2.1.15.7", 5, 1);
	}
	
	public void testV1EnterpriseIdAndGenericAndSpecificMissWithWrongSpecific() throws Exception {
		anticipateAndSend(null, "v1",
				".1.3.6.1.2.1.15.7", 6, 50);
	}
	
	public void testV1GenericMatch() throws Exception {
		anticipateAndSend("uei.opennms.org/generic/traps/SNMP_Cold_Start",
				"v1", null, 0, 0);
	}
	
	public void testV2GenericMatch() throws Exception {
		anticipateAndSend("uei.opennms.org/generic/traps/SNMP_Cold_Start",
				"v2c", ".1.3.6.1.6.3.1.1.5.1", 0, 0);
	}
	
	public void testV1TrapDroppedEvent() throws Exception {
		anticipateAndSend(null, "v1", ".1.3.6.1.2.1.15.7", 6, 2);
	}
	
	public void testV2TrapDroppedEvent() throws Exception {
		anticipateAndSend(null, "v2c", ".1.3.6.1.2.1.15.7", 6, 2);
	}
	
	public void testV1TrapDefaultEvent() throws Exception {
		anticipateAndSend(null, "v1", null, 6, 1);
	}
	
	public void testV2TrapDefaultEvent() throws Exception {
		anticipateAndSend(null, "v2c", null, 6, 1);
	}
	
	public void testV1TrapDroppedIPEvent() throws Exception {
		anticipateAndSend(null, "v1", null, 0, 0, "192.168.1.1");
	}
	
	public void testV1TrapNotDroppedIPOffEvent() throws Exception {
		anticipateAndSend("uei.opennms.org/generic/traps/SNMP_Cold_Start",
				"v1", null, 0, 0, "192.168.1.2");
	}
	
	public void testV1TrapDroppedNetwork1Event() throws Exception {
		anticipateAndSend(null, "v1", ".1.3.6.1.2.1.15.7", 6, 1, "192.168.1.1");
	}
	
	public void testV1TrapDroppedNetwork2Event() throws Exception {
		anticipateAndSend(null,
				"v1", ".1.3.6.1.2.1.15.7", 6, 1, "192.168.1.2");
	}
	
	public void testV1TrapNotDroppedNetworkOffEvent() throws Exception {
		anticipateAndSend("uei.opennms.org/IETF/BGP/traps/bgpEstablished",
				"v1", ".1.3.6.1.2.1.15.7", 6, 1, "192.168.2.1");
	}
	
	public Event createEvent(String uei) {
		Event event = new Event();
		event.setInterface("127.0.0.1");
		event.setNodeid(0);
		event.setUei(uei);
		System.out.println("Event created: " + new EventWrapper(event));
		return event;
	}
	
	public Event createEvent(String version, String enterprise, int generic, int specific) {
		Event event = new Event();
		
		event.setInterface("127.0.0.1");
		event.setNodeid(0);
		
		Snmp snmp = new Snmp();
		snmp.setVersion(version);
		snmp.setId(enterprise);
		snmp.setGeneric(generic);
		snmp.setSpecific(specific);
		event.setSnmp(snmp);
		
		event.setSnmphost("127.0.0.1");

		/* System.out.println("Event created: " + new EventWrapper(event)); */
		
		return event;
	}
	
	public Event createEvent(String version, String enterprise, int generic, int specific,
			String snmphost) {
		Event event = createEvent(version, enterprise, generic, specific);
		
		event.setSnmphost(snmphost);
		
		return event;
	}
	
	public void anticipateAndSend(String event,
			String version, String enterprise, int generic, int specific) throws Exception {
		Event snmp = createEvent(version, enterprise, generic, specific);
		anticipateAndSend(event, snmp);
	}
	
	public void anticipateAndSend(String event,
			String version, String enterprise, int generic, int specific, String snmphost)
			throws Exception {
		Event snmp = createEvent(version, enterprise, generic, specific, snmphost);
		anticipateAndSend(event, snmp);
	}
	
	public void anticipateAndSend(String event, Event snmp) {
		/*
		if (event != null) {
			createEvent(event);
		}
		*/
		
		org.opennms.netmgt.xml.eventconf.Event econf = EventConfigurationManager.get(snmp);
		
		System.out.println("Eventconf: " + (econf == null ? null : new EventConfWrapper(econf) ));
		
		if (event != null) {
			if (econf == null) {
				fail("Was expecting to match an eventconf with a UEI of \"" + event +
						"\", but no matching eventconf was found.");
			} else {
				if (!event.equals(econf.getUei())) {
					fail("Was expecting to match an eventconf with a UEI of \"" + event +
							"\", but received an eventconf with a UEI of \"" + econf.getUei() +
							"\"");
				}
				// everything's fine
			}
		} else {
			if (econf != null) {
				boolean complain = true;
				
				Logmsg logmsg = econf.getLogmsg();
				if (logmsg != null) {
					String dest = logmsg.getDest();
					if ("discardtraps".equals(dest)) {
						complain = false;
					}
				}

				if (complain) {
					fail("Was not expecting an eventconf, but received an eventconf with " +
							"an UEI of \"" + econf.getUei() + "\"");
				}
			}
			// everything's fine
		}
		
		finishUp();
	}
	
}