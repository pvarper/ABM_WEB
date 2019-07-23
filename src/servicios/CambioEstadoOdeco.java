/**
 * CambioEstadoOdeco.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class CambioEstadoOdeco  implements java.io.Serializable {
    private java.lang.String SERIE;

    private java.lang.String ESTADO;

    public CambioEstadoOdeco() {
    }

    public CambioEstadoOdeco(
           java.lang.String SERIE,
           java.lang.String ESTADO) {
           this.SERIE = SERIE;
           this.ESTADO = ESTADO;
    }


    /**
     * Gets the SERIE value for this CambioEstadoOdeco.
     * 
     * @return SERIE
     */
    public java.lang.String getSERIE() {
        return SERIE;
    }


    /**
     * Sets the SERIE value for this CambioEstadoOdeco.
     * 
     * @param SERIE
     */
    public void setSERIE(java.lang.String SERIE) {
        this.SERIE = SERIE;
    }


    /**
     * Gets the ESTADO value for this CambioEstadoOdeco.
     * 
     * @return ESTADO
     */
    public java.lang.String getESTADO() {
        return ESTADO;
    }


    /**
     * Sets the ESTADO value for this CambioEstadoOdeco.
     * 
     * @param ESTADO
     */
    public void setESTADO(java.lang.String ESTADO) {
        this.ESTADO = ESTADO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CambioEstadoOdeco)) return false;
        CambioEstadoOdeco other = (CambioEstadoOdeco) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SERIE==null && other.getSERIE()==null) || 
             (this.SERIE!=null &&
              this.SERIE.equals(other.getSERIE()))) &&
            ((this.ESTADO==null && other.getESTADO()==null) || 
             (this.ESTADO!=null &&
              this.ESTADO.equals(other.getESTADO())));
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
        if (getSERIE() != null) {
            _hashCode += getSERIE().hashCode();
        }
        if (getESTADO() != null) {
            _hashCode += getESTADO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CambioEstadoOdeco.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "CambioEstadoOdeco"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERIE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERIE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ESTADO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ESTADO"));
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
