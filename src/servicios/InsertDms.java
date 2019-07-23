/**
 * InsertDms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertDms  implements java.io.Serializable {
    private java.lang.String USUARIO;

    private java.lang.String TELEFONO;

    private java.lang.String NOMBRES;

    private java.lang.String APELLIDOS;

    private java.lang.String EMAIL;

    private java.lang.String PERFIL;

    private java.lang.String VISIBILIDAD;

    public InsertDms() {
    }

    public InsertDms(
           java.lang.String USUARIO,
           java.lang.String TELEFONO,
           java.lang.String NOMBRES,
           java.lang.String APELLIDOS,
           java.lang.String EMAIL,
           java.lang.String PERFIL,
           java.lang.String VISIBILIDAD) {
           this.USUARIO = USUARIO;
           this.TELEFONO = TELEFONO;
           this.NOMBRES = NOMBRES;
           this.APELLIDOS = APELLIDOS;
           this.EMAIL = EMAIL;
           this.PERFIL = PERFIL;
           this.VISIBILIDAD = VISIBILIDAD;
    }


    /**
     * Gets the USUARIO value for this InsertDms.
     * 
     * @return USUARIO
     */
    public java.lang.String getUSUARIO() {
        return USUARIO;
    }


    /**
     * Sets the USUARIO value for this InsertDms.
     * 
     * @param USUARIO
     */
    public void setUSUARIO(java.lang.String USUARIO) {
        this.USUARIO = USUARIO;
    }


    /**
     * Gets the TELEFONO value for this InsertDms.
     * 
     * @return TELEFONO
     */
    public java.lang.String getTELEFONO() {
        return TELEFONO;
    }


    /**
     * Sets the TELEFONO value for this InsertDms.
     * 
     * @param TELEFONO
     */
    public void setTELEFONO(java.lang.String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }


    /**
     * Gets the NOMBRES value for this InsertDms.
     * 
     * @return NOMBRES
     */
    public java.lang.String getNOMBRES() {
        return NOMBRES;
    }


    /**
     * Sets the NOMBRES value for this InsertDms.
     * 
     * @param NOMBRES
     */
    public void setNOMBRES(java.lang.String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }


    /**
     * Gets the APELLIDOS value for this InsertDms.
     * 
     * @return APELLIDOS
     */
    public java.lang.String getAPELLIDOS() {
        return APELLIDOS;
    }


    /**
     * Sets the APELLIDOS value for this InsertDms.
     * 
     * @param APELLIDOS
     */
    public void setAPELLIDOS(java.lang.String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }


    /**
     * Gets the EMAIL value for this InsertDms.
     * 
     * @return EMAIL
     */
    public java.lang.String getEMAIL() {
        return EMAIL;
    }


    /**
     * Sets the EMAIL value for this InsertDms.
     * 
     * @param EMAIL
     */
    public void setEMAIL(java.lang.String EMAIL) {
        this.EMAIL = EMAIL;
    }


    /**
     * Gets the PERFIL value for this InsertDms.
     * 
     * @return PERFIL
     */
    public java.lang.String getPERFIL() {
        return PERFIL;
    }


    /**
     * Sets the PERFIL value for this InsertDms.
     * 
     * @param PERFIL
     */
    public void setPERFIL(java.lang.String PERFIL) {
        this.PERFIL = PERFIL;
    }


    /**
     * Gets the VISIBILIDAD value for this InsertDms.
     * 
     * @return VISIBILIDAD
     */
    public java.lang.String getVISIBILIDAD() {
        return VISIBILIDAD;
    }


    /**
     * Sets the VISIBILIDAD value for this InsertDms.
     * 
     * @param VISIBILIDAD
     */
    public void setVISIBILIDAD(java.lang.String VISIBILIDAD) {
        this.VISIBILIDAD = VISIBILIDAD;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertDms)) return false;
        InsertDms other = (InsertDms) obj;
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
            ((this.TELEFONO==null && other.getTELEFONO()==null) || 
             (this.TELEFONO!=null &&
              this.TELEFONO.equals(other.getTELEFONO()))) &&
            ((this.NOMBRES==null && other.getNOMBRES()==null) || 
             (this.NOMBRES!=null &&
              this.NOMBRES.equals(other.getNOMBRES()))) &&
            ((this.APELLIDOS==null && other.getAPELLIDOS()==null) || 
             (this.APELLIDOS!=null &&
              this.APELLIDOS.equals(other.getAPELLIDOS()))) &&
            ((this.EMAIL==null && other.getEMAIL()==null) || 
             (this.EMAIL!=null &&
              this.EMAIL.equals(other.getEMAIL()))) &&
            ((this.PERFIL==null && other.getPERFIL()==null) || 
             (this.PERFIL!=null &&
              this.PERFIL.equals(other.getPERFIL()))) &&
            ((this.VISIBILIDAD==null && other.getVISIBILIDAD()==null) || 
             (this.VISIBILIDAD!=null &&
              this.VISIBILIDAD.equals(other.getVISIBILIDAD())));
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
        if (getTELEFONO() != null) {
            _hashCode += getTELEFONO().hashCode();
        }
        if (getNOMBRES() != null) {
            _hashCode += getNOMBRES().hashCode();
        }
        if (getAPELLIDOS() != null) {
            _hashCode += getAPELLIDOS().hashCode();
        }
        if (getEMAIL() != null) {
            _hashCode += getEMAIL().hashCode();
        }
        if (getPERFIL() != null) {
            _hashCode += getPERFIL().hashCode();
        }
        if (getVISIBILIDAD() != null) {
            _hashCode += getVISIBILIDAD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertDms.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "InsertDms"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USUARIO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USUARIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TELEFONO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TELEFONO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOMBRES");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NOMBRES"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("APELLIDOS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "APELLIDOS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMAIL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EMAIL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PERFIL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PERFIL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VISIBILIDAD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VISIBILIDAD"));
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
