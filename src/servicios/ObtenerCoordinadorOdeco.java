/**
 * ObtenerCoordinadorOdeco.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class ObtenerCoordinadorOdeco  implements java.io.Serializable {
    private java.lang.String rol_id;

    public ObtenerCoordinadorOdeco() {
    }

    public ObtenerCoordinadorOdeco(
           java.lang.String rol_id) {
           this.rol_id = rol_id;
    }


    /**
     * Gets the rol_id value for this ObtenerCoordinadorOdeco.
     * 
     * @return rol_id
     */
    public java.lang.String getRol_id() {
        return rol_id;
    }


    /**
     * Sets the rol_id value for this ObtenerCoordinadorOdeco.
     * 
     * @param rol_id
     */
    public void setRol_id(java.lang.String rol_id) {
        this.rol_id = rol_id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerCoordinadorOdeco)) return false;
        ObtenerCoordinadorOdeco other = (ObtenerCoordinadorOdeco) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rol_id==null && other.getRol_id()==null) || 
             (this.rol_id!=null &&
              this.rol_id.equals(other.getRol_id())));
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
        if (getRol_id() != null) {
            _hashCode += getRol_id().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerCoordinadorOdeco.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "obtenerCoordinadorOdeco"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rol_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rol_id"));
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
