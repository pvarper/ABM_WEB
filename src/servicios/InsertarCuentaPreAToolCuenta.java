/**
 * InsertarCuentaPreAToolCuenta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertarCuentaPreAToolCuenta  implements java.io.Serializable {
    private java.lang.String CUENTA;

    private java.lang.String LOCALIDAD;

    public InsertarCuentaPreAToolCuenta() {
    }

    public InsertarCuentaPreAToolCuenta(
           java.lang.String CUENTA,
           java.lang.String LOCALIDAD) {
           this.CUENTA = CUENTA;
           this.LOCALIDAD = LOCALIDAD;
    }


    /**
     * Gets the CUENTA value for this InsertarCuentaPreAToolCuenta.
     * 
     * @return CUENTA
     */
    public java.lang.String getCUENTA() {
        return CUENTA;
    }


    /**
     * Sets the CUENTA value for this InsertarCuentaPreAToolCuenta.
     * 
     * @param CUENTA
     */
    public void setCUENTA(java.lang.String CUENTA) {
        this.CUENTA = CUENTA;
    }


    /**
     * Gets the LOCALIDAD value for this InsertarCuentaPreAToolCuenta.
     * 
     * @return LOCALIDAD
     */
    public java.lang.String getLOCALIDAD() {
        return LOCALIDAD;
    }


    /**
     * Sets the LOCALIDAD value for this InsertarCuentaPreAToolCuenta.
     * 
     * @param LOCALIDAD
     */
    public void setLOCALIDAD(java.lang.String LOCALIDAD) {
        this.LOCALIDAD = LOCALIDAD;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertarCuentaPreAToolCuenta)) return false;
        InsertarCuentaPreAToolCuenta other = (InsertarCuentaPreAToolCuenta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.CUENTA==null && other.getCUENTA()==null) || 
             (this.CUENTA!=null &&
              this.CUENTA.equals(other.getCUENTA()))) &&
            ((this.LOCALIDAD==null && other.getLOCALIDAD()==null) || 
             (this.LOCALIDAD!=null &&
              this.LOCALIDAD.equals(other.getLOCALIDAD())));
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
        if (getCUENTA() != null) {
            _hashCode += getCUENTA().hashCode();
        }
        if (getLOCALIDAD() != null) {
            _hashCode += getLOCALIDAD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertarCuentaPreAToolCuenta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "insertarCuentaPreAToolCuenta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUENTA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUENTA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOCALIDAD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOCALIDAD"));
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
