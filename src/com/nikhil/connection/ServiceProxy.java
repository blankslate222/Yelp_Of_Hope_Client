package com.nikhil.connection;

public class ServiceProxy implements com.nikhil.connection.Service {
  private String _endpoint = null;
  private com.nikhil.connection.Service service = null;
  
  public ServiceProxy() {
    _initServiceProxy();
  }
  
  public ServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceProxy();
  }
  
  private void _initServiceProxy() {
    try {
      service = (new com.nikhil.connection.ServiceServiceLocator()).getService();
      if (service != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service != null)
      ((javax.xml.rpc.Stub)service)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.nikhil.connection.Service getService() {
    if (service == null)
      _initServiceProxy();
    return service;
  }
  
  public com.nikhil.bean.ElementBean[] getElement() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getElement();
  }
  
  public com.nikhil.bean.CategoryBean[] getCategory() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getCategory();
  }
  
  public java.lang.String updateCategory(java.lang.String idVal, com.nikhil.bean.CategoryBean cat) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.updateCategory(idVal, cat);
  }
  
  public java.lang.String newElement(com.nikhil.bean.ElementBean ele) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.newElement(ele);
  }
  
  public com.nikhil.bean.CategoryBean[] getCategoryDetails(java.lang.String keyCol, java.lang.String keyVal) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getCategoryDetails(keyCol, keyVal);
  }
  
  public com.nikhil.bean.ElementBean[] getElementDetails(java.lang.String keyCol, java.lang.String keyVal) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getElementDetails(keyCol, keyVal);
  }
  
  public java.lang.String deleteElement(java.lang.String keyVal) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.deleteElement(keyVal);
  }
  
  public java.lang.String updateElement(java.lang.String idVal, com.nikhil.bean.ElementBean ele) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.updateElement(idVal, ele);
  }
  
  public java.lang.String newCategory(com.nikhil.bean.CategoryBean cat) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.newCategory(cat);
  }
  
  public java.lang.String deleteCategory(java.lang.String keyVal) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.deleteCategory(keyVal);
  }
  
  public com.nikhil.bean.ReviewBean[] getReview() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getReview();
  }
  
  public com.nikhil.bean.SignInBean signIn(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.signIn(user, password);
  }
  
  public com.nikhil.bean.UserBean[] getUserDetails(java.lang.String keyVal) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getUserDetails(keyVal);
  }
  
  public java.lang.String newUser(com.nikhil.bean.UserBean usr) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.newUser(usr);
  }
  
  public java.lang.String newReview(com.nikhil.bean.ReviewBean rev) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.newReview(rev);
  }
  
  public com.nikhil.bean.ReviewBean[] getUserReview(java.lang.String keyCol, java.lang.String keyVal) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getUserReview(keyCol, keyVal);
  }
  
  
}