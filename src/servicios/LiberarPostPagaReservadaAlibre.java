/**
 * LiberarPostPagaReservadaAlibre.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class LiberarPostPagaReservadaAlibre  implements java.io.Serializable {
    private java.lang.String CUENTA;

    private java.lang.String TEXTO;

    public LiberarPostPagaReservadaAlibre() {
    }

    public LiberarPostPagaReservadaAlibre(
           java.lang.String CUENTA,
           java.lang.String TEXTO) {
           this.CUENTA = CUENTA;
           this.TEXTO = TEXTO;
    }


    /**
     * Gets the CUENTA value for this LiberarPostPagaReservadaAlibre.
     * 
     * @return CUENTA
     */
    public java.lang.String getCUENTA() {
        return CUENTA;
    }


    /**
     * Sets the CUENTA value for this LiberarPostPagaReservadaAlibre.
     * 
     * @param CUENTA
     */
    public void setCUENTA(java.lang.String CUENTA) {
        this.CUENTA = CUENTA;
    }


    /**
     * Gets the TEXTO value for this LiberarPostPagaReservadaAlibre.
     * 
     * @return TEXTO
     */
    public java.lang.String getTEXTO() {
        return TEXTO;
    }


    /**
     * Sets the TEXTO value for this LiberarPostPagaReservadaAlibre.
     * 
     * @param TEXTO
     */
    public void setTEXTO(java.lang.String TEXTO) {
        this.TEXTO = TEXTO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiberarPostPagaReservadaAlibre)) return false;
        LiberarPostPagaReservadaAlibre other = (LiberarPostPagaReservadaAlibre) obj;
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
            ((this.TEXTO==null && other.getTEXTO()==null) || 
             (this.TEXTO!=null &&
              this.TEXTO.equals(other.getTEXTO())));
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
        if (getTEXTO() != null) {
            _hashCode += getTEXTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiberarPostPagaReservadaAlibre.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "liberarPostPagaReservadaAlibre"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUENTA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUENTA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TEXTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TEXTO"));
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
