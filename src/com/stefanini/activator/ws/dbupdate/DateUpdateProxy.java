package com.stefanini.activator.ws.dbupdate;

public class DateUpdateProxy implements com.stefanini.activator.ws.dbupdate.DateUpdate {
  private String _endpoint = null;
  private com.stefanini.activator.ws.dbupdate.DateUpdate dateUpdate = null;
  
  public DateUpdateProxy() {
    _initDateUpdateProxy();
  }
  
  public DateUpdateProxy(String endpoint) {
    _endpoint = endpoint;
    _initDateUpdateProxy();
  }
  
  private void _initDateUpdateProxy() {
    try {
      dateUpdate = (new com.stefanini.activator.ws.dbupdate.DateUpdateServiceLocator()).getDateUpdatePort();
      if (dateUpdate != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dateUpdate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dateUpdate)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dateUpdate != null)
      ((javax.xml.rpc.Stub)dateUpdate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.stefanini.activator.ws.dbupdate.DateUpdate getDateUpdate() {
    if (dateUpdate == null)
      _initDateUpdateProxy();
    return dateUpdate;
  }
  
  public java.lang.String updateDate(java.lang.String msisdn, java.util.Calendar date) throws java.rmi.RemoteException{
    if (dateUpdate == null)
      _initDateUpdateProxy();
    return dateUpdate.updateDate(msisdn, date);
  }
  
  
}