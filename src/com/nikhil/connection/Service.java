/**
 * Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nikhil.connection;

public interface Service extends java.rmi.Remote {
    public com.nikhil.bean.ElementBean[] getElement() throws java.rmi.RemoteException;
    public com.nikhil.bean.CategoryBean[] getCategory() throws java.rmi.RemoteException;
    public java.lang.String updateCategory(java.lang.String idVal, com.nikhil.bean.CategoryBean cat) throws java.rmi.RemoteException;
    public java.lang.String newElement(com.nikhil.bean.ElementBean ele) throws java.rmi.RemoteException;
    public com.nikhil.bean.CategoryBean[] getCategoryDetails(java.lang.String keyCol, java.lang.String keyVal) throws java.rmi.RemoteException;
    public com.nikhil.bean.ElementBean[] getElementDetails(java.lang.String keyCol, java.lang.String keyVal) throws java.rmi.RemoteException;
    public java.lang.String deleteElement(java.lang.String keyVal) throws java.rmi.RemoteException;
    public java.lang.String updateElement(java.lang.String idVal, com.nikhil.bean.ElementBean ele) throws java.rmi.RemoteException;
    public java.lang.String newCategory(com.nikhil.bean.CategoryBean cat) throws java.rmi.RemoteException;
    public java.lang.String deleteCategory(java.lang.String keyVal) throws java.rmi.RemoteException;
    public com.nikhil.bean.ReviewBean[] getReview() throws java.rmi.RemoteException;
    public com.nikhil.bean.SignInBean signIn(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;
    public com.nikhil.bean.UserBean[] getUserDetails(java.lang.String keyVal) throws java.rmi.RemoteException;
    public java.lang.String newUser(com.nikhil.bean.UserBean usr) throws java.rmi.RemoteException;
    public java.lang.String newReview(com.nikhil.bean.ReviewBean rev) throws java.rmi.RemoteException;
    public com.nikhil.bean.ReviewBean[] getUserReview(java.lang.String keyCol, java.lang.String keyVal) throws java.rmi.RemoteException;
}
