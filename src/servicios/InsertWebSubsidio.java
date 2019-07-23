/**
 * InsertWebSubsidio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertWebSubsidio  implements java.io.Serializable {
    private java.lang.String LOGIN;

    private java.lang.String NOMBRE;

    private java.lang.String IDDPTO;

    private java.lang.String EMAIL;

    private java.lang.String ROL;

    public InsertWebSubsidio() {
    }

    public InsertWebSubsidio(
           java.lang.String LOGIN,
           java.lang.String NOMBRE,
           java.lang.String IDDPTO,
           java.lang.String EMAIL,
           java.lang.String ROL) {
           this.LOGIN = LOGIN;
           this.NOMBRE = NOMBRE;
           this.IDDPTO = IDDPTO;
           this.EMAIL = EMAIL;
           this.ROL = ROL;
    }


    /**
     * Gets the LOGIN value for this InsertWebSubsidio.
     * 
     * @return LOGIN
     */
    public java.lang.String getLOGIN() {
        return LOGIN;
    }


    /**
     * Sets the LOGIN value for this InsertWebSubsidio.
     * 
     * @param LOGIN
     */
    public void setLOGIN(java.lang.String LOGIN) {
        this.LOGIN = LOGIN;
    }


    /**
     * Gets the NOMBRE value for this InsertWebSubsidio.
     * 
     * @return NOMBRE
     */
    public java.lang.String getNOMBRE() {
        return NOMBRE;
    }


    /**
     * Sets the NOMBRE value for this InsertWebSubsidio.
     * 
     * @param NOMBRE
     */
    public void setNOMBRE(java.lang.String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }


    /**
     * Gets the IDDPTO value for this InsertWebSubsidio.
     * 
     * @return IDDPTO
     */
    public java.lang.String getIDDPTO() {
        return IDDPTO;
    }


    /**
     * Sets the IDDPTO value for this InsertWebSubsidio.
     * 
     * @param IDDPTO
     */
    public void setIDDPTO(java.lang.String IDDPTO) {
        this.IDDPTO = IDDPTO;
    }


    /**
     * Gets the EMAIL value for this InsertWebSubsidio.
     * 
     * @return EMAIL
     */
    public java.lang.String getEMAIL() {
        return EMAIL;
    }


    /**
     * Sets the EMAIL value for this InsertWebSubsidio.
     * 
     * @param EMAIL
     */
    public void setEMAIL(java.lang.String EMAIL) {
        this.EMAIL = EMAIL;
    }


    /**
     * Gets the ROL value for this InsertWebSubsidio.
     * 
     * @return ROL
     */
    public java.lang.String getROL() {
        return ROL;
    }


    /**
     * Sets the ROL value for this InsertWebSubsidio.
     * 
     * @param ROL
     */
    public void setROL(java.lang.String ROL) {
        this.ROL = ROL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertWebSubsidio)) return false;
        InsertWebSubsidio other = (InsertWebSubsidio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.LOGIN==null && other.getLOGIN()==null) || 
             (this.LOGIN!=null &&
              this.LOGIN.equals(other.getLOGIN()))) &&
            ((this.NOMBRE==null && other.getNOMBRE()==null) || 
             (this.NOMBRE!=null &&
              this.NOMBRE.equals(other.getNOMBRE()))) &&
            ((this.IDDPTO==null && other.getIDDPTO()==null) || 
             (this.IDDPTO!=null &&
              this.IDDPTO.equals(other.getIDDPTO()))) &&
            ((this.EMAIL==null && other.getEMAIL()==null) || 
             (this.EMAIL!=null &&
              this.EMAIL.equals(other.getEMAIL()))) &&
            ((this.ROL==null && other.getROL()==null) || 
             (this.ROL!=null &&
              this.ROL.equals(other.getROL())));
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
        if (getLOGIN() != null) {
            _hashCode += getLOGIN().hashCode();
        }
        if (getNOMBRE() != null) {
            _hashCode += getNOMBRE().hashCode();
        }
        if (getIDDPTO() != null) {
            _hashCode += getIDDPTO().hashCode();
        }
        if (getEMAIL() != null) {
            _hashCode += getEMAIL().hashCode();
        }
        if (getROL() != null) {
            _hashCode += getROL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertWebSubsidio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "InsertWebSubsidio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOGIN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOGIN"));
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
        elemField.setFieldName("IDDPTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IDDPTO"));
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
        elemField.setFieldName("ROL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ROL"));
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
