package lejos.hardware.sensor;

import lejos.hardware.port.AnalogPort;
import lejos.hardware.port.Port;
import lejos.robotics.PressureDetector;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * Support for Dexter Industries DPressure500
 * Not tested.
 * 
 * @author Lawrie Griffiths
 *
 */
public class DPressure500 extends AnalogSensor implements SensorConstants, PressureDetector {
	/*
	 * Formula from DPRESS-driver.h:
	 * vRef = 4.85
	 * vOut = rawValue * vRef / 1023
	 * result = (vOut / vRef - CAL1) / CAL2
	 */
	private static final double CAL1 = 0.04;
	private static final double CAL2 = 0.0018;
	
	/* 
	 * Optimized:
	 * result = rawValue * DPRESS_MULT - DPRESS_OFFSET;
	 */
	private static final float DPRESS_MULT = (float)(1.0 / CAL2 / 1023);
	private static final float DPRESS_OFFSET = (float)(CAL1 / CAL2);
	
    public DPressure500(AnalogPort port) {
        super(port);
        port.setTypeAndMode(TYPE_CUSTOM, MODE_RAW);
    }
    
    public DPressure500(Port port) {
        super(port);
        this.port.setTypeAndMode(TYPE_CUSTOM, MODE_RAW);
    }
    
    /**
     * Get the pressure reading in kilopascals
     * 
     * @return the pressure in kPa
     */
    public float getPressure() {
    	return port.readRawValue() * DPRESS_MULT - DPRESS_OFFSET;
    }
}