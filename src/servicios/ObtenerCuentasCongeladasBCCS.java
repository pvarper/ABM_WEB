/**
 * ObtenerCuentasCongeladasBCCS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class ObtenerCuentasCongeladasBCCS  implements java.io.Serializable {
    private java.lang.String LOCALIDAD;

    private java.lang.String MODALIDAD;

    private java.lang.Integer CANTIDAD;

    public ObtenerCuentasCongeladasBCCS() {
    }

    public ObtenerCuentasCongeladasBCCS(
           java.lang.String LOCALIDAD,
           java.lang.String MODALIDAD,
           java.lang.Integer CANTIDAD) {
           this.LOCALIDAD = LOCALIDAD;
           this.MODALIDAD = MODALIDAD;
           this.CANTIDAD = CANTIDAD;
    }


    /**
     * Gets the LOCALIDAD value for this ObtenerCuentasCongeladasBCCS.
     * 
     * @return LOCALIDAD
     */
    public java.lang.String getLOCALIDAD() {
        return LOCALIDAD;
    }


    /**
     * Sets the LOCALIDAD value for this ObtenerCuentasCongeladasBCCS.
     * 
     * @param LOCALIDAD
     */
    public void setLOCALIDAD(java.lang.String LOCALIDAD) {
        this.LOCALIDAD = LOCALIDAD;
    }


    /**
     * Gets the MODALIDAD value for this ObtenerCuentasCongeladasBCCS.
     * 
     * @return MODALIDAD
     */
    public java.lang.String getMODALIDAD() {
        return MODALIDAD;
    }


    /**
     * Sets the MODALIDAD value for this ObtenerCuentasCongeladasBCCS.
     * 
     * @param MODALIDAD
     */
    public void setMODALIDAD(java.lang.String MODALIDAD) {
        this.MODALIDAD = MODALIDAD;
    }


    /**
     * Gets the CANTIDAD value for this ObtenerCuentasCongeladasBCCS.
     * 
     * @return CANTIDAD
     */
    public java.lang.Integer getCANTIDAD() {
        return CANTIDAD;
    }


    /**
     * Sets the CANTIDAD value for this ObtenerCuentasCongeladasBCCS.
     * 
     * @param CANTIDAD
     */
    public void setCANTIDAD(java.lang.Integer CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerCuentasCongeladasBCCS)) return false;
        ObtenerCuentasCongeladasBCCS other = (ObtenerCuentasCongeladasBCCS) obj;
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
            ((this.MODALIDAD==null && other.getMODALIDAD()==null) || 
             (this.MODALIDAD!=null &&
              this.MODALIDAD.equals(other.getMODALIDAD()))) &&
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
        if (getMODALIDAD() != null) {
            _hashCode += getMODALIDAD().hashCode();
        }
        if (getCANTIDAD() != null) {
            _hashCode += getCANTIDAD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerCuentasCongeladasBCCS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "obtenerCuentasCongeladasBCCS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOCALIDAD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOCALIDAD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MODALIDAD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MODALIDAD"));
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
