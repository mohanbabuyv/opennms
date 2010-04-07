package org.opennms.features.poller.remote.gwt.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;

public class LocationMonitorStateTest {
	private static int count = 0;

	@Test
	public void testAllMonitorsStarted() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		for (int i = 0; i < 5; i++) {
			monitors.add(getMonitor("STARTED"));
		}
		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertTrue("all location monitors should be STARTED", lms.allMonitorsStarted());

		monitors.add(getMonitor("CONFIG_CHANGED"));
		lms = new LocationMonitorState(monitors, null);
		assertTrue("all location monitors should be STARTED or CONFIG_CHANGED", lms.allMonitorsStarted());
		
		monitors.add(getMonitor("DISCONNECTED"));
		lms = new LocationMonitorState(monitors, null);
		assertFalse("at least one monitor is not STARTED or CONFIG_CHANGED", lms.allMonitorsStarted());
	}

	@Test
	public void testAtLeastOneMonitorStarted() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		for (int i = 0; i < 5; i++) {
			monitors.add(getMonitor("DISCONNECTED"));
		}
		monitors.add(getMonitor("CONFIG_CHANGED"));
		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertTrue("at least one monitor is CONFIG_CHANGED or STARTED", lms.atLeastOneMonitorStarted());

		monitors.add(getMonitor("STARTED"));
		lms = new LocationMonitorState(monitors, null);
		assertTrue("at least one monitor is CONFIG_CHANGED or STARTED", lms.atLeastOneMonitorStarted());
	}
	
	@Test
	public void testAllButOneMonitorsDisconnected() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		for (int i = 0; i < 5; i++) {
			monitors.add(getMonitor("DISCONNECTED"));
		}
		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertFalse("all monitors should be DISCONNECTED", lms.allButOneMonitorsDisconnected());
		
		monitors.add(getMonitor("STARTED"));
		lms = new LocationMonitorState(monitors, null);
		assertTrue("all but one monitors are DISCONNECTED", lms.allButOneMonitorsDisconnected());
		
		monitors.add(getMonitor("CONFIG_CHANGED"));
		lms = new LocationMonitorState(monitors, null);
		assertFalse("more than one monitor is STARTED or CONFIG_CHANGED", lms.allButOneMonitorsDisconnected());
	}

	@Test
	public void testMarkerStatusGreen() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		Collection<GWTLocationSpecificStatus> statuses = new ArrayList<GWTLocationSpecificStatus>();
		for (int i = 0; i< 5; i++) {
			GWTLocationMonitor monitor = getMonitor("STARTED");
			monitors.add(monitor);

			GWTLocationSpecificStatus status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("HTTP"));
			status.setPollResult(GWTPollResult.available(100));
			statuses.add(status);
			
			status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("FTP"));
			status.setPollResult(GWTPollResult.available(150));
			statuses.add(status);
		}
		
		LocationMonitorState lms = new LocationMonitorState(monitors, statuses);
		assertEquals("status should be up", ServiceStatus.UP, lms.getStatus());
	}

	@Test
	public void testMarkerStatusAllButOneNonStoppedDisconnected() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		monitors.add(getMonitor("STARTED"));
		monitors.add(getMonitor("DISCONNECTED"));
		monitors.add(getMonitor("DISCONNECTED"));
		monitors.add(getMonitor("DISCONNECTED"));

		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertEquals("status should be marginal if only one monitor started, and more than one disconnected", ServiceStatus.MARGINAL, lms.getStatus());
		
	}

	@Test
	public void testMarkerStatusSomeReportDownStatus() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		Collection<GWTLocationSpecificStatus> statuses = new ArrayList<GWTLocationSpecificStatus>();

		GWTLocationMonitor monitor = getMonitor("STARTED");
		monitors.add(monitor);

		GWTLocationSpecificStatus status = new GWTLocationSpecificStatus();
		status.setId(++count);
		status.setLocationMonitor(monitor);
		status.setMonitoredService(getService("HTTP"));
		status.setPollResult(GWTPollResult.available(100));
		statuses.add(status);

		status = new GWTLocationSpecificStatus();
		status.setId(++count);
		status.setLocationMonitor(monitor);
		status.setMonitoredService(getService("FTP"));
		status.setPollResult(GWTPollResult.down("failure to yield to oncoming traffic"));
		statuses.add(status);

		monitor = getMonitor("STARTED");
		monitors.add(monitor);

		status = new GWTLocationSpecificStatus();
		status.setId(++count);
		status.setLocationMonitor(monitor);
		status.setMonitoredService(getService("HTTP"));
		status.setPollResult(GWTPollResult.down("trouble in paradise"));
		statuses.add(status);

		status = new GWTLocationSpecificStatus();
		status.setId(++count);
		status.setLocationMonitor(monitor);
		status.setMonitoredService(getService("FTP"));
		status.setPollResult(GWTPollResult.available(150));
		statuses.add(status);

		LocationMonitorState lms = new LocationMonitorState(monitors, statuses);
		assertEquals("status should be marginal when some services are down", ServiceStatus.MARGINAL, lms.getStatus());
	}

	@Test
	public void testMarkerStatusOneOfTwoServicesDown() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		Collection<GWTLocationSpecificStatus> statuses = new ArrayList<GWTLocationSpecificStatus>();
		for (int i = 0; i< 5; i++) {
			GWTLocationMonitor monitor = getMonitor("STARTED");
			monitors.add(monitor);

			GWTLocationSpecificStatus status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("HTTP"));
			status.setPollResult(GWTPollResult.available(100));
			statuses.add(status);
			
			status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("FTP"));
			status.setPollResult(GWTPollResult.down("totally busted!"));
			statuses.add(status);
		}
		
		LocationMonitorState lms = new LocationMonitorState(monitors, statuses);
		assertEquals("status should be down when one service is down across all monitors", ServiceStatus.DOWN, lms.getStatus());
	}

	@Test
	public void testMarkerStatusOneServiceDown() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		Collection<GWTLocationSpecificStatus> statuses = new ArrayList<GWTLocationSpecificStatus>();
		for (int i = 0; i< 5; i++) {
			GWTLocationMonitor monitor = getMonitor("STARTED");
			monitors.add(monitor);

			GWTLocationSpecificStatus status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("HTTP"));
			status.setPollResult(GWTPollResult.down("completely wacked"));
			statuses.add(status);
		}
		
		LocationMonitorState lms = new LocationMonitorState(monitors, statuses);
		assertEquals("status should be down when one (of one) service is down across all monitors", ServiceStatus.DOWN, lms.getStatus());
	}

	@Test
	public void testMarkerStatusTwoOfTwoServicesDown() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		Collection<GWTLocationSpecificStatus> statuses = new ArrayList<GWTLocationSpecificStatus>();
		for (int i = 0; i< 5; i++) {
			GWTLocationMonitor monitor = getMonitor("STARTED");
			monitors.add(monitor);

			GWTLocationSpecificStatus status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("HTTP"));
			status.setPollResult(GWTPollResult.down("exploded"));
			statuses.add(status);
			
			status = new GWTLocationSpecificStatus();
			status.setId(++count);
			status.setLocationMonitor(monitor);
			status.setMonitoredService(getService("FTP"));
			status.setPollResult(GWTPollResult.down("casters up"));
			statuses.add(status);
		}
		
		LocationMonitorState lms = new LocationMonitorState(monitors, statuses);
		assertEquals("status should be down when two services (of two) are down across all monitors", ServiceStatus.DOWN, lms.getStatus());
	}

	@Test
	public void testMarkerStatusAllNonStoppedMonitorsDisconnected() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		monitors.add(getMonitor("STOPPED"));
		monitors.add(getMonitor("STOPPED"));
		monitors.add(getMonitor("DISCONNECTED"));
		monitors.add(getMonitor("DISCONNECTED"));
		monitors.add(getMonitor("DISCONNECTED"));

		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertEquals("status should be unknown if all monitors are either disconnected or stopped", ServiceStatus.UNKNOWN, lms.getStatus());
	}

	@Test
	public void testMarkerStatusAllRegisteredMonitorsDisconnected() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		monitors.add(getMonitor("DISCONNECTED"));
		monitors.add(getMonitor("DISCONNECTED"));
		monitors.add(getMonitor("DISCONNECTED"));

		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertEquals("status should be unknown if all registered monitors are disconnected", ServiceStatus.UNKNOWN, lms.getStatus());
	}

	@Test
	public void testMarkerStatusOnlyOneRegisteredMonitorIsStopped() {
		Collection<GWTLocationMonitor> monitors = new ArrayList<GWTLocationMonitor>();
		monitors.add(getMonitor("STOPPED"));
		
		LocationMonitorState lms = new LocationMonitorState(monitors, null);
		assertEquals("single stopped monitor should be unknown", ServiceStatus.UNKNOWN, lms.getStatus());
	}

	private GWTLocationMonitor getMonitor(String status) {
		GWTLocationMonitor monitor = new GWTLocationMonitor();
		monitor.setDefinitionName("RDU");
		monitor.setId(count++);
		monitor.setLastCheckInTime(new Date());
		monitor.setStatus(status);
		return monitor;
	}

	private GWTMonitoredService getService(String serviceName) {
		GWTMonitoredService service = new GWTMonitoredService();
		service.setId(++count);
		service.setServiceName(serviceName);
		return service;
	}
}
