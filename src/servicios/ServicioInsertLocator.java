/**
 * ServicioInsertLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class ServicioInsertLocator extends org.apache.axis.client.Service implements servicios.ServicioInsert {

    public ServicioInsertLocator() {
    }


    public ServicioInsertLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioInsertLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiciosPort
    private java.lang.String ServiciosPort_address = "http://csappsupport:8086/WSInsert-war/ServicioInsert/Servicios";

    public java.lang.String getServiciosPortAddress() {
        return ServiciosPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiciosPortWSDDServiceName = "ServiciosPort";

    public java.lang.String getServiciosPortWSDDServiceName() {
        return ServiciosPortWSDDServiceName;
    }

    public void setServiciosPortWSDDServiceName(java.lang.String name) {
        ServiciosPortWSDDServiceName = name;
    }

    public servicios.Servicios getServiciosPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiciosPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiciosPort(endpoint);
    }

    public servicios.Servicios getServiciosPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            servicios.ServicioInsertSoapBindingStub _stub = new servicios.ServicioInsertSoapBindingStub(portAddress, this);
            _stub.setPortName(getServiciosPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiciosPortEndpointAddress(java.lang.String address) {
        ServiciosPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (servicios.Servicios.class.isAssignableFrom(serviceEndpointInterface)) {
                servicios.ServicioInsertSoapBindingStub _stub = new servicios.ServicioInsertSoapBindingStub(new java.net.URL(ServiciosPort_address), this);
                _stub.setPortName(getServiciosPortWSDDServiceName());
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
        if ("ServiciosPort".equals(inputPortName)) {
            return getServiciosPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servicios/", "ServicioInsert");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servicios/", "ServiciosPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiciosPort".equals(portName)) {
            setServiciosPortEndpointAddress(address);
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
