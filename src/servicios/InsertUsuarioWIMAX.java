/**
 * InsertUsuarioWIMAX.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertUsuarioWIMAX  implements java.io.Serializable {
    private java.lang.String GRUPO;

    private java.lang.String USUARIO;

    private java.lang.String APELLIDOS;

    private java.lang.String EHUMANO;

    private java.lang.String DEPARTAMENTO;

    private java.lang.String AREA;

    private java.lang.String TIPO_COMPROBANTE;

    public InsertUsuarioWIMAX() {
    }

    public InsertUsuarioWIMAX(
           java.lang.String GRUPO,
           java.lang.String USUARIO,
           java.lang.String APELLIDOS,
           java.lang.String EHUMANO,
           java.lang.String DEPARTAMENTO,
           java.lang.String AREA,
           java.lang.String TIPO_COMPROBANTE) {
           this.GRUPO = GRUPO;
           this.USUARIO = USUARIO;
           this.APELLIDOS = APELLIDOS;
           this.EHUMANO = EHUMANO;
           this.DEPARTAMENTO = DEPARTAMENTO;
           this.AREA = AREA;
           this.TIPO_COMPROBANTE = TIPO_COMPROBANTE;
    }


    /**
     * Gets the GRUPO value for this InsertUsuarioWIMAX.
     * 
     * @return GRUPO
     */
    public java.lang.String getGRUPO() {
        return GRUPO;
    }


    /**
     * Sets the GRUPO value for this InsertUsuarioWIMAX.
     * 
     * @param GRUPO
     */
    public void setGRUPO(java.lang.String GRUPO) {
        this.GRUPO = GRUPO;
    }


    /**
     * Gets the USUARIO value for this InsertUsuarioWIMAX.
     * 
     * @return USUARIO
     */
    public java.lang.String getUSUARIO() {
        return USUARIO;
    }


    /**
     * Sets the USUARIO value for this InsertUsuarioWIMAX.
     * 
     * @param USUARIO
     */
    public void setUSUARIO(java.lang.String USUARIO) {
        this.USUARIO = USUARIO;
    }


    /**
     * Gets the APELLIDOS value for this InsertUsuarioWIMAX.
     * 
     * @return APELLIDOS
     */
    public java.lang.String getAPELLIDOS() {
        return APELLIDOS;
    }


    /**
     * Sets the APELLIDOS value for this InsertUsuarioWIMAX.
     * 
     * @param APELLIDOS
     */
    public void setAPELLIDOS(java.lang.String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }


    /**
     * Gets the EHUMANO value for this InsertUsuarioWIMAX.
     * 
     * @return EHUMANO
     */
    public java.lang.String getEHUMANO() {
        return EHUMANO;
    }


    /**
     * Sets the EHUMANO value for this InsertUsuarioWIMAX.
     * 
     * @param EHUMANO
     */
    public void setEHUMANO(java.lang.String EHUMANO) {
        this.EHUMANO = EHUMANO;
    }


    /**
     * Gets the DEPARTAMENTO value for this InsertUsuarioWIMAX.
     * 
     * @return DEPARTAMENTO
     */
    public java.lang.String getDEPARTAMENTO() {
        return DEPARTAMENTO;
    }


    /**
     * Sets the DEPARTAMENTO value for this InsertUsuarioWIMAX.
     * 
     * @param DEPARTAMENTO
     */
    public void setDEPARTAMENTO(java.lang.String DEPARTAMENTO) {
        this.DEPARTAMENTO = DEPARTAMENTO;
    }


    /**
     * Gets the AREA value for this InsertUsuarioWIMAX.
     * 
     * @return AREA
     */
    public java.lang.String getAREA() {
        return AREA;
    }


    /**
     * Sets the AREA value for this InsertUsuarioWIMAX.
     * 
     * @param AREA
     */
    public void setAREA(java.lang.String AREA) {
        this.AREA = AREA;
    }


    /**
     * Gets the TIPO_COMPROBANTE value for this InsertUsuarioWIMAX.
     * 
     * @return TIPO_COMPROBANTE
     */
    public java.lang.String getTIPO_COMPROBANTE() {
        return TIPO_COMPROBANTE;
    }


    /**
     * Sets the TIPO_COMPROBANTE value for this InsertUsuarioWIMAX.
     * 
     * @param TIPO_COMPROBANTE
     */
    public void setTIPO_COMPROBANTE(java.lang.String TIPO_COMPROBANTE) {
        this.TIPO_COMPROBANTE = TIPO_COMPROBANTE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertUsuarioWIMAX)) return false;
        InsertUsuarioWIMAX other = (InsertUsuarioWIMAX) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.GRUPO==null && other.getGRUPO()==null) || 
             (this.GRUPO!=null &&
              this.GRUPO.equals(other.getGRUPO()))) &&
            ((this.USUARIO==null && other.getUSUARIO()==null) || 
             (this.USUARIO!=null &&
              this.USUARIO.equals(other.getUSUARIO()))) &&
            ((this.APELLIDOS==null && other.getAPELLIDOS()==null) || 
             (this.APELLIDOS!=null &&
              this.APELLIDOS.equals(other.getAPELLIDOS()))) &&
            ((this.EHUMANO==null && other.getEHUMANO()==null) || 
             (this.EHUMANO!=null &&
              this.EHUMANO.equals(other.getEHUMANO()))) &&
            ((this.DEPARTAMENTO==null && other.getDEPARTAMENTO()==null) || 
             (this.DEPARTAMENTO!=null &&
              this.DEPARTAMENTO.equals(other.getDEPARTAMENTO()))) &&
            ((this.AREA==null && other.getAREA()==null) || 
             (this.AREA!=null &&
              this.AREA.equals(other.getAREA()))) &&
            ((this.TIPO_COMPROBANTE==null && other.getTIPO_COMPROBANTE()==null) || 
             (this.TIPO_COMPROBANTE!=null &&
              this.TIPO_COMPROBANTE.equals(other.getTIPO_COMPROBANTE())));
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
        if (getGRUPO() != null) {
            _hashCode += getGRUPO().hashCode();
        }
        if (getUSUARIO() != null) {
            _hashCode += getUSUARIO().hashCode();
        }
        if (getAPELLIDOS() != null) {
            _hashCode += getAPELLIDOS().hashCode();
        }
        if (getEHUMANO() != null) {
            _hashCode += getEHUMANO().hashCode();
        }
        if (getDEPARTAMENTO() != null) {
            _hashCode += getDEPARTAMENTO().hashCode();
        }
        if (getAREA() != null) {
            _hashCode += getAREA().hashCode();
        }
        if (getTIPO_COMPROBANTE() != null) {
            _hashCode += getTIPO_COMPROBANTE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertUsuarioWIMAX.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "InsertUsuarioWIMAX"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GRUPO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GRUPO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USUARIO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USUARIO"));
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
        elemField.setFieldName("EHUMANO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EHUMANO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEPARTAMENTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEPARTAMENTO"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TIPO_COMPROBANTE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TIPO_COMPROBANTE"));
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
