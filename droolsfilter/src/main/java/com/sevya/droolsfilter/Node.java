package com.sevya.droolsfilter;

public class Node implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//observed readings
	private String nodeIP;
	private long batteryVoltage;
	private int faultFlag;
	
	//derived values
	private int nodeCounter;
	private int nodeFaultFlag;
	
	//internal evaluation flags
	private int evalNodeBatteryVoltageFlag;
	
		
		
	
	public String getNodeIP() {
		return nodeIP;
	}
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	public long getBatteryVoltage() {
		return batteryVoltage;
	}
	public void setBatteryVoltage(long batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}
	public int getFaultFlag() {
		return faultFlag;
	}
	public void setFaultFlag(int faultFlag) {
		this.faultFlag = faultFlag;
	}
	public int getNodeCounter() {
		return nodeCounter;
	}
	public void setNodeCounter(int nodeCounter) {
		this.nodeCounter = nodeCounter;
	}
	public int getNodeFaultFlag() {
		return nodeFaultFlag;
	}
	public void setNodeFaultFlag(int nodeFaultFlag) {
		this.nodeFaultFlag = nodeFaultFlag;
	}
	public int getEvalNodeBatteryVoltageFlag() {
		return evalNodeBatteryVoltageFlag;
	}
	public void setEvalNodeBatteryVoltageFlag(int evalNodeBatteryVoltageFlag) {
		this.evalNodeBatteryVoltageFlag = evalNodeBatteryVoltageFlag;
	}
	
	
}
