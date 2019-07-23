/**
 * CambioFecha.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class CambioFecha  implements java.io.Serializable {
    private java.lang.String COMPROBANTE;

    private java.lang.String SERIE;

    private int NRO;

    private java.lang.String FECHA_INICIAL;

    private java.lang.String FECHA_FINAL;

    public CambioFecha() {
    }

    public CambioFecha(
           java.lang.String COMPROBANTE,
           java.lang.String SERIE,
           int NRO,
           java.lang.String FECHA_INICIAL,
           java.lang.String FECHA_FINAL) {
           this.COMPROBANTE = COMPROBANTE;
           this.SERIE = SERIE;
           this.NRO = NRO;
           this.FECHA_INICIAL = FECHA_INICIAL;
           this.FECHA_FINAL = FECHA_FINAL;
    }


    /**
     * Gets the COMPROBANTE value for this CambioFecha.
     * 
     * @return COMPROBANTE
     */
    public java.lang.String getCOMPROBANTE() {
        return COMPROBANTE;
    }


    /**
     * Sets the COMPROBANTE value for this CambioFecha.
     * 
     * @param COMPROBANTE
     */
    public void setCOMPROBANTE(java.lang.String COMPROBANTE) {
        this.COMPROBANTE = COMPROBANTE;
    }


    /**
     * Gets the SERIE value for this CambioFecha.
     * 
     * @return SERIE
     */
    public java.lang.String getSERIE() {
        return SERIE;
    }


    /**
     * Sets the SERIE value for this CambioFecha.
     * 
     * @param SERIE
     */
    public void setSERIE(java.lang.String SERIE) {
        this.SERIE = SERIE;
    }


    /**
     * Gets the NRO value for this CambioFecha.
     * 
     * @return NRO
     */
    public int getNRO() {
        return NRO;
    }


    /**
     * Sets the NRO value for this CambioFecha.
     * 
     * @param NRO
     */
    public void setNRO(int NRO) {
        this.NRO = NRO;
    }


    /**
     * Gets the FECHA_INICIAL value for this CambioFecha.
     * 
     * @return FECHA_INICIAL
     */
    public java.lang.String getFECHA_INICIAL() {
        return FECHA_INICIAL;
    }


    /**
     * Sets the FECHA_INICIAL value for this CambioFecha.
     * 
     * @param FECHA_INICIAL
     */
    public void setFECHA_INICIAL(java.lang.String FECHA_INICIAL) {
        this.FECHA_INICIAL = FECHA_INICIAL;
    }


    /**
     * Gets the FECHA_FINAL value for this CambioFecha.
     * 
     * @return FECHA_FINAL
     */
    public java.lang.String getFECHA_FINAL() {
        return FECHA_FINAL;
    }


    /**
     * Sets the FECHA_FINAL value for this CambioFecha.
     * 
     * @param FECHA_FINAL
     */
    public void setFECHA_FINAL(java.lang.String FECHA_FINAL) {
        this.FECHA_FINAL = FECHA_FINAL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CambioFecha)) return false;
        CambioFecha other = (CambioFecha) obj;
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
            ((this.FECHA_INICIAL==null && other.getFECHA_INICIAL()==null) || 
             (this.FECHA_INICIAL!=null &&
              this.FECHA_INICIAL.equals(other.getFECHA_INICIAL()))) &&
            ((this.FECHA_FINAL==null && other.getFECHA_FINAL()==null) || 
             (this.FECHA_FINAL!=null &&
              this.FECHA_FINAL.equals(other.getFECHA_FINAL())));
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
        if (getFECHA_INICIAL() != null) {
            _hashCode += getFECHA_INICIAL().hashCode();
        }
        if (getFECHA_FINAL() != null) {
            _hashCode += getFECHA_FINAL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CambioFecha.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "cambioFecha"));
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
        elemField.setFieldName("FECHA_INICIAL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FECHA_INICIAL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FECHA_FINAL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FECHA_FINAL"));
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
