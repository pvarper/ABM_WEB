/**
 * NivelacionBodega.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class NivelacionBodega  implements java.io.Serializable {
    private int SUCURSAL;

    private int LOCAL;

    private int DEPOSITO;

    private int CODIGO_PROUCTO;

    public NivelacionBodega() {
    }

    public NivelacionBodega(
           int SUCURSAL,
           int LOCAL,
           int DEPOSITO,
           int CODIGO_PROUCTO) {
           this.SUCURSAL = SUCURSAL;
           this.LOCAL = LOCAL;
           this.DEPOSITO = DEPOSITO;
           this.CODIGO_PROUCTO = CODIGO_PROUCTO;
    }


    /**
     * Gets the SUCURSAL value for this NivelacionBodega.
     * 
     * @return SUCURSAL
     */
    public int getSUCURSAL() {
        return SUCURSAL;
    }


    /**
     * Sets the SUCURSAL value for this NivelacionBodega.
     * 
     * @param SUCURSAL
     */
    public void setSUCURSAL(int SUCURSAL) {
        this.SUCURSAL = SUCURSAL;
    }


    /**
     * Gets the LOCAL value for this NivelacionBodega.
     * 
     * @return LOCAL
     */
    public int getLOCAL() {
        return LOCAL;
    }


    /**
     * Sets the LOCAL value for this NivelacionBodega.
     * 
     * @param LOCAL
     */
    public void setLOCAL(int LOCAL) {
        this.LOCAL = LOCAL;
    }


    /**
     * Gets the DEPOSITO value for this NivelacionBodega.
     * 
     * @return DEPOSITO
     */
    public int getDEPOSITO() {
        return DEPOSITO;
    }


    /**
     * Sets the DEPOSITO value for this NivelacionBodega.
     * 
     * @param DEPOSITO
     */
    public void setDEPOSITO(int DEPOSITO) {
        this.DEPOSITO = DEPOSITO;
    }


    /**
     * Gets the CODIGO_PROUCTO value for this NivelacionBodega.
     * 
     * @return CODIGO_PROUCTO
     */
    public int getCODIGO_PROUCTO() {
        return CODIGO_PROUCTO;
    }


    /**
     * Sets the CODIGO_PROUCTO value for this NivelacionBodega.
     * 
     * @param CODIGO_PROUCTO
     */
    public void setCODIGO_PROUCTO(int CODIGO_PROUCTO) {
        this.CODIGO_PROUCTO = CODIGO_PROUCTO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NivelacionBodega)) return false;
        NivelacionBodega other = (NivelacionBodega) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.SUCURSAL == other.getSUCURSAL() &&
            this.LOCAL == other.getLOCAL() &&
            this.DEPOSITO == other.getDEPOSITO() &&
            this.CODIGO_PROUCTO == other.getCODIGO_PROUCTO();
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
        _hashCode += getSUCURSAL();
        _hashCode += getLOCAL();
        _hashCode += getDEPOSITO();
        _hashCode += getCODIGO_PROUCTO();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NivelacionBodega.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "nivelacionBodega"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SUCURSAL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SUCURSAL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOCAL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOCAL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEPOSITO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEPOSITO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CODIGO_PROUCTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CODIGO_PROUCTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
