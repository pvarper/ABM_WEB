/**
 * DateUpdateServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stefanini.activator.ws.dbupdate;

public class DateUpdateServiceLocator extends org.apache.axis.client.Service implements com.stefanini.activator.ws.dbupdate.DateUpdateService {

    public DateUpdateServiceLocator() {
    }


    public DateUpdateServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DateUpdateServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DateUpdatePort
    private java.lang.String DateUpdatePort_address = "http://172.29.94.21:7024/ActivatorUtilities/DateUpdateService";
    //PRUEBA http://172.29.94.21:7024/ActivatorUtilities/DateUpdateService?wsdl
    
    
    public java.lang.String getDateUpdatePortAddress() {
        return DateUpdatePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DateUpdatePortWSDDServiceName = "DateUpdatePort";

    public java.lang.String getDateUpdatePortWSDDServiceName() {
        return DateUpdatePortWSDDServiceName;
    }

    public void setDateUpdatePortWSDDServiceName(java.lang.String name) {
        DateUpdatePortWSDDServiceName = name;
    }

    public com.stefanini.activator.ws.dbupdate.DateUpdate getDateUpdatePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DateUpdatePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDateUpdatePort(endpoint);
    }

    public com.stefanini.activator.ws.dbupdate.DateUpdate getDateUpdatePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.stefanini.activator.ws.dbupdate.DateUpdatePortBindingStub _stub = new com.stefanini.activator.ws.dbupdate.DateUpdatePortBindingStub(portAddress, this);
            _stub.setPortName(getDateUpdatePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDateUpdatePortEndpointAddress(java.lang.String address) {
        DateUpdatePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.stefanini.activator.ws.dbupdate.DateUpdate.class.isAssignableFrom(serviceEndpointInterface)) {
                com.stefanini.activator.ws.dbupdate.DateUpdatePortBindingStub _stub = new com.stefanini.activator.ws.dbupdate.DateUpdatePortBindingStub(new java.net.URL(DateUpdatePort_address), this);
                _stub.setPortName(getDateUpdatePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DateUpdatePort".equals(inputPortName)) {
            return getDateUpdatePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dbupdate.ws.activator.stefanini.com/", "DateUpdateService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dbupdate.ws.activator.stefanini.com/", "DateUpdatePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DateUpdatePort".equals(portName)) {
            setDateUpdatePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
