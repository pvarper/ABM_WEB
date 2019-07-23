/**
 * ObtenerComandosCuenta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class ObtenerComandosCuenta  implements java.io.Serializable {
    private java.lang.String CUENTA;

    private java.lang.String FECHA;

    public ObtenerComandosCuenta() {
    }

    public ObtenerComandosCuenta(
           java.lang.String CUENTA,
           java.lang.String FECHA) {
           this.CUENTA = CUENTA;
           this.FECHA = FECHA;
    }


    /**
     * Gets the CUENTA value for this ObtenerComandosCuenta.
     * 
     * @return CUENTA
     */
    public java.lang.String getCUENTA() {
        return CUENTA;
    }


    /**
     * Sets the CUENTA value for this ObtenerComandosCuenta.
     * 
     * @param CUENTA
     */
    public void setCUENTA(java.lang.String CUENTA) {
        this.CUENTA = CUENTA;
    }


    /**
     * Gets the FECHA value for this ObtenerComandosCuenta.
     * 
     * @return FECHA
     */
    public java.lang.String getFECHA() {
        return FECHA;
    }


    /**
     * Sets the FECHA value for this ObtenerComandosCuenta.
     * 
     * @param FECHA
     */
    public void setFECHA(java.lang.String FECHA) {
        this.FECHA = FECHA;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerComandosCuenta)) return false;
        ObtenerComandosCuenta other = (ObtenerComandosCuenta) obj;
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
            ((this.FECHA==null && other.getFECHA()==null) || 
             (this.FECHA!=null &&
              this.FECHA.equals(other.getFECHA())));
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
        if (getFECHA() != null) {
            _hashCode += getFECHA().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerComandosCuenta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "obtenerComandosCuenta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUENTA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUENTA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FECHA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FECHA"));
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
