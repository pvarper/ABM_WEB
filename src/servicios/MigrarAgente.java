/**
 * MigrarAgente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class MigrarAgente  implements java.io.Serializable {
    private java.lang.String id_Staff_vacacion;

    private java.lang.String id_Staff_Recepcion;

    private int id_Opercacion;

    public MigrarAgente() {
    }

    public MigrarAgente(
           java.lang.String id_Staff_vacacion,
           java.lang.String id_Staff_Recepcion,
           int id_Opercacion) {
           this.id_Staff_vacacion = id_Staff_vacacion;
           this.id_Staff_Recepcion = id_Staff_Recepcion;
           this.id_Opercacion = id_Opercacion;
    }


    /**
     * Gets the id_Staff_vacacion value for this MigrarAgente.
     * 
     * @return id_Staff_vacacion
     */
    public java.lang.String getId_Staff_vacacion() {
        return id_Staff_vacacion;
    }


    /**
     * Sets the id_Staff_vacacion value for this MigrarAgente.
     * 
     * @param id_Staff_vacacion
     */
    public void setId_Staff_vacacion(java.lang.String id_Staff_vacacion) {
        this.id_Staff_vacacion = id_Staff_vacacion;
    }


    /**
     * Gets the id_Staff_Recepcion value for this MigrarAgente.
     * 
     * @return id_Staff_Recepcion
     */
    public java.lang.String getId_Staff_Recepcion() {
        return id_Staff_Recepcion;
    }


    /**
     * Sets the id_Staff_Recepcion value for this MigrarAgente.
     * 
     * @param id_Staff_Recepcion
     */
    public void setId_Staff_Recepcion(java.lang.String id_Staff_Recepcion) {
        this.id_Staff_Recepcion = id_Staff_Recepcion;
    }


    /**
     * Gets the id_Opercacion value for this MigrarAgente.
     * 
     * @return id_Opercacion
     */
    public int getId_Opercacion() {
        return id_Opercacion;
    }


    /**
     * Sets the id_Opercacion value for this MigrarAgente.
     * 
     * @param id_Opercacion
     */
    public void setId_Opercacion(int id_Opercacion) {
        this.id_Opercacion = id_Opercacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MigrarAgente)) return false;
        MigrarAgente other = (MigrarAgente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id_Staff_vacacion==null && other.getId_Staff_vacacion()==null) || 
             (this.id_Staff_vacacion!=null &&
              this.id_Staff_vacacion.equals(other.getId_Staff_vacacion()))) &&
            ((this.id_Staff_Recepcion==null && other.getId_Staff_Recepcion()==null) || 
             (this.id_Staff_Recepcion!=null &&
              this.id_Staff_Recepcion.equals(other.getId_Staff_Recepcion()))) &&
            this.id_Opercacion == other.getId_Opercacion();
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
        if (getId_Staff_vacacion() != null) {
            _hashCode += getId_Staff_vacacion().hashCode();
        }
        if (getId_Staff_Recepcion() != null) {
            _hashCode += getId_Staff_Recepcion().hashCode();
        }
        _hashCode += getId_Opercacion();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MigrarAgente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "migrarAgente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_Staff_vacacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_Staff_vacacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_Staff_Recepcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_Staff_Recepcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_Opercacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_Opercacion"));
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
