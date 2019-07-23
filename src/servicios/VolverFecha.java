/**
 * VolverFecha.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class VolverFecha  implements java.io.Serializable {
    private java.lang.String COMPROBANTE;

    private java.lang.String SERIE;

    private int NRO;

    private java.lang.String FECHA;

    public VolverFecha() {
    }

    public VolverFecha(
           java.lang.String COMPROBANTE,
           java.lang.String SERIE,
           int NRO,
           java.lang.String FECHA) {
           this.COMPROBANTE = COMPROBANTE;
           this.SERIE = SERIE;
           this.NRO = NRO;
           this.FECHA = FECHA;
    }


    /**
     * Gets the COMPROBANTE value for this VolverFecha.
     * 
     * @return COMPROBANTE
     */
    public java.lang.String getCOMPROBANTE() {
        return COMPROBANTE;
    }


    /**
     * Sets the COMPROBANTE value for this VolverFecha.
     * 
     * @param COMPROBANTE
     */
    public void setCOMPROBANTE(java.lang.String COMPROBANTE) {
        this.COMPROBANTE = COMPROBANTE;
    }


    /**
     * Gets the SERIE value for this VolverFecha.
     * 
     * @return SERIE
     */
    public java.lang.String getSERIE() {
        return SERIE;
    }


    /**
     * Sets the SERIE value for this VolverFecha.
     * 
     * @param SERIE
     */
    public void setSERIE(java.lang.String SERIE) {
        this.SERIE = SERIE;
    }


    /**
     * Gets the NRO value for this VolverFecha.
     * 
     * @return NRO
     */
    public int getNRO() {
        return NRO;
    }


    /**
     * Sets the NRO value for this VolverFecha.
     * 
     * @param NRO
     */
    public void setNRO(int NRO) {
        this.NRO = NRO;
    }


    /**
     * Gets the FECHA value for this VolverFecha.
     * 
     * @return FECHA
     */
    public java.lang.String getFECHA() {
        return FECHA;
    }


    /**
     * Sets the FECHA value for this VolverFecha.
     * 
     * @param FECHA
     */
    public void setFECHA(java.lang.String FECHA) {
        this.FECHA = FECHA;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VolverFecha)) return false;
        VolverFecha other = (VolverFecha) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.COMPROBANTE==null && other.getCOMPROBANTE()==null) || 
             (this.COMPROBANTE!=null &&
              this.COMPROBANTE.equals(other.getCOMPROBANTE()))) &&
            ((this.SERIE==null && other.getSERIE()==null) || 
             (this.SERIE!=null &&
              this.SERIE.equals(other.getSERIE()))) &&
            this.NRO == other.getNRO() &&
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
        if (getCOMPROBANTE() != null) {
            _hashCode += getCOMPROBANTE().hashCode();
        }
        if (getSERIE() != null) {
            _hashCode += getSERIE().hashCode();
        }
        _hashCode += getNRO();
        if (getFECHA() != null) {
            _hashCode += getFECHA().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VolverFecha.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "volverFecha"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("COMPROBANTE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "COMPROBANTE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERIE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERIE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NRO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NRO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
