/**
 * LogFechaCuenta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class LogFechaCuenta  implements java.io.Serializable {
    private java.lang.String user;

    private java.lang.String cuenta;

    private java.lang.String newFecha;

    private java.lang.String oldFecha;

    public LogFechaCuenta() {
    }

    public LogFechaCuenta(
           java.lang.String user,
           java.lang.String cuenta,
           java.lang.String newFecha,
           java.lang.String oldFecha) {
           this.user = user;
           this.cuenta = cuenta;
           this.newFecha = newFecha;
           this.oldFecha = oldFecha;
    }


    /**
     * Gets the user value for this LogFechaCuenta.
     * 
     * @return user
     */
    public java.lang.String getUser() {
        return user;
    }


    /**
     * Sets the user value for this LogFechaCuenta.
     * 
     * @param user
     */
    public void setUser(java.lang.String user) {
        this.user = user;
    }


    /**
     * Gets the cuenta value for this LogFechaCuenta.
     * 
     * @return cuenta
     */
    public java.lang.String getCuenta() {
        return cuenta;
    }


    /**
     * Sets the cuenta value for this LogFechaCuenta.
     * 
     * @param cuenta
     */
    public void setCuenta(java.lang.String cuenta) {
        this.cuenta = cuenta;
    }


    /**
     * Gets the newFecha value for this LogFechaCuenta.
     * 
     * @return newFecha
     */
    public java.lang.String getNewFecha() {
        return newFecha;
    }


    /**
     * Sets the newFecha value for this LogFechaCuenta.
     * 
     * @param newFecha
     */
    public void setNewFecha(java.lang.String newFecha) {
        this.newFecha = newFecha;
    }


    /**
     * Gets the oldFecha value for this LogFechaCuenta.
     * 
     * @return oldFecha
     */
    public java.lang.String getOldFecha() {
        return oldFecha;
    }


    /**
     * Sets the oldFecha value for this LogFechaCuenta.
     * 
     * @param oldFecha
     */
    public void setOldFecha(java.lang.String oldFecha) {
        this.oldFecha = oldFecha;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogFechaCuenta)) return false;
        LogFechaCuenta other = (LogFechaCuenta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser()))) &&
            ((this.cuenta==null && other.getCuenta()==null) || 
             (this.cuenta!=null &&
              this.cuenta.equals(other.getCuenta()))) &&
            ((this.newFecha==null && other.getNewFecha()==null) || 
             (this.newFecha!=null &&
              this.newFecha.equals(other.getNewFecha()))) &&
            ((this.oldFecha==null && other.getOldFecha()==null) || 
             (this.oldFecha!=null &&
              this.oldFecha.equals(other.getOldFecha())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        if (getCuenta() != null) {
            _hashCode += getCuenta().hashCode();
        }
        if (getNewFecha() != null) {
            _hashCode += getNewFecha().hashCode();
        }
        if (getOldFecha() != null) {
            _hashCode += getOldFecha().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogFechaCuenta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "logFechaCuenta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newFecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "newFecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldFecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldFecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
