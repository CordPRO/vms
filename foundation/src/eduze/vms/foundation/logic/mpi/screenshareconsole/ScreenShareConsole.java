/**
 * ScreenShareConsole.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eduze.vms.foundation.logic.mpi.screenshareconsole;

public interface ScreenShareConsole extends java.rmi.Remote {
    public java.lang.String getConsoleId() throws java.rmi.RemoteException;
    public int getUpdateInterval() throws java.rmi.RemoteException;
    public void setUpdateInterval(int arg0) throws java.rmi.RemoteException;
    public boolean isEnabled() throws java.rmi.RemoteException;
}