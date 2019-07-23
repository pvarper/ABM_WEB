/**
 * InsertUsuarioASGenerico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertUsuarioASGenerico  implements java.io.Serializable {
    private java.lang.String USUARIO;

    private java.lang.String NOMBRE;

    private java.lang.String AREA;

    public InsertUsuarioASGenerico() {
    }

    public InsertUsuarioASGenerico(
           java.lang.String USUARIO,
           java.lang.String NOMBRE,
           java.lang.String AREA) {
           this.USUARIO = USUARIO;
           this.NOMBRE = NOMBRE;
           this.AREA = AREA;
    }


    /**
     * Gets the USUARIO value for this InsertUsuarioASGenerico.
     * 
     * @return USUARIO
     */
    public java.lang.String getUSUARIO() {
        return USUARIO;
    }


    /**
     * Sets the USUARIO value for this InsertUsuarioASGenerico.
     * 
     * @param USUARIO
     */
    public void setUSUARIO(java.lang.String USUARIO) {
        this.USUARIO = USUARIO;
    }


    /**
     * Gets the NOMBRE value for this InsertUsuarioASGenerico.
     * 
     * @return NOMBRE
     */
    public java.lang.String getNOMBRE() {
        return NOMBRE;
    }


    /**
     * Sets the NOMBRE value for this InsertUsuarioASGenerico.
     * 
     * @param NOMBRE
     */
    public void setNOMBRE(java.lang.String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }


    /**
     * Gets the AREA value for this InsertUsuarioASGenerico.
     * 
     * @return AREA
     */
    public java.lang.String getAREA() {
        return AREA;
    }


    /**
     * Sets the AREA value for this InsertUsuarioASGenerico.
     * 
     * @param AREA
     */
    public void setAREA(java.lang.String AREA) {
        this.AREA = AREA;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertUsuarioASGenerico)) return false;
        InsertUsuarioASGenerico other = (InsertUsuarioASGenerico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.USUARIO==null && other.getUSUARIO()==null) || 
             (this.USUARIO!=null &&
              this.USUARIO.equals(other.getUSUARIO()))) &&
            ((this.NOMBRE==null && other.getNOMBRE()==null) || 
             (this.NOMBRE!=null &&
              this.NOMBRE.equals(other.getNOMBRE()))) &&
            ((this.AREA==null && other.getAREA()==null) || 
             (this.AREA!=null &&
              this.AREA.equals(other.getAREA())));
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
        if (getUSUARIO() != null) {
            _hashCode += getUSUARIO().hashCode();
        }
        if (getNOMBRE() != null) {
            _hashCode += getNOMBRE().hashCode();
        }
        if (getAREA() != null) {
            _hashCode += getAREA().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertUsuarioASGenerico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "InsertUsuarioASGenerico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USUARIO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USUARIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOMBRE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NOMBRE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AREA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AREA"));
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
