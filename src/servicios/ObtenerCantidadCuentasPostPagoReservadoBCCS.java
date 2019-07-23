/**
 * ObtenerCantidadCuentasPostPagoReservadoBCCS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class ObtenerCantidadCuentasPostPagoReservadoBCCS  implements java.io.Serializable {
    private java.lang.String LOCALIDAD;

    private java.lang.Integer CANTIDAD;

    public ObtenerCantidadCuentasPostPagoReservadoBCCS() {
    }

    public ObtenerCantidadCuentasPostPagoReservadoBCCS(
           java.lang.String LOCALIDAD,
           java.lang.Integer CANTIDAD) {
           this.LOCALIDAD = LOCALIDAD;
           this.CANTIDAD = CANTIDAD;
    }


    /**
     * Gets the LOCALIDAD value for this ObtenerCantidadCuentasPostPagoReservadoBCCS.
     * 
     * @return LOCALIDAD
     */
    public java.lang.String getLOCALIDAD() {
        return LOCALIDAD;
    }


    /**
     * Sets the LOCALIDAD value for this ObtenerCantidadCuentasPostPagoReservadoBCCS.
     * 
     * @param LOCALIDAD
     */
    public void setLOCALIDAD(java.lang.String LOCALIDAD) {
        this.LOCALIDAD = LOCALIDAD;
    }


    /**
     * Gets the CANTIDAD value for this ObtenerCantidadCuentasPostPagoReservadoBCCS.
     * 
     * @return CANTIDAD
     */
    public java.lang.Integer getCANTIDAD() {
        return CANTIDAD;
    }


    /**
     * Sets the CANTIDAD value for this ObtenerCantidadCuentasPostPagoReservadoBCCS.
     * 
     * @param CANTIDAD
     */
    public void setCANTIDAD(java.lang.Integer CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerCantidadCuentasPostPagoReservadoBCCS)) return false;
        ObtenerCantidadCuentasPostPagoReservadoBCCS other = (ObtenerCantidadCuentasPostPagoReservadoBCCS) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.LOCALIDAD==null && other.getLOCALIDAD()==null) || 
             (this.LOCALIDAD!=null &&
              this.LOCALIDAD.equals(other.getLOCALIDAD()))) &&
            ((this.CANTIDAD==null && other.getCANTIDAD()==null) || 
             (this.CANTIDAD!=null &&
              this.CANTIDAD.equals(other.getCANTIDAD())));
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
        if (getLOCALIDAD() != null) {
            _hashCode += getLOCALIDAD().hashCode();
        }
        if (getCANTIDAD() != null) {
            _hashCode += getCANTIDAD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerCantidadCuentasPostPagoReservadoBCCS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "obtenerCantidadCuentasPostPagoReservadoBCCS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOCALIDAD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOCALIDAD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CANTIDAD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CANTIDAD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
