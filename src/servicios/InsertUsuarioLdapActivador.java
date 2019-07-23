/**
 * InsertUsuarioLdapActivador.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertUsuarioLdapActivador  implements java.io.Serializable {
    private java.lang.String USERNAME;

    private java.lang.String FULLNAME;

    private java.lang.String EMAIL;

    private java.lang.String PHONE;

    private java.lang.String VENDOR_DOC_NUMBER;

    private java.lang.String ROL;

    public InsertUsuarioLdapActivador() {
    }

    public InsertUsuarioLdapActivador(
           java.lang.String USERNAME,
           java.lang.String FULLNAME,
           java.lang.String EMAIL,
           java.lang.String PHONE,
           java.lang.String VENDOR_DOC_NUMBER,
           java.lang.String ROL) {
           this.USERNAME = USERNAME;
           this.FULLNAME = FULLNAME;
           this.EMAIL = EMAIL;
           this.PHONE = PHONE;
           this.VENDOR_DOC_NUMBER = VENDOR_DOC_NUMBER;
           this.ROL = ROL;
    }


    /**
     * Gets the USERNAME value for this InsertUsuarioLdapActivador.
     * 
     * @return USERNAME
     */
    public java.lang.String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this InsertUsuarioLdapActivador.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(java.lang.String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the FULLNAME value for this InsertUsuarioLdapActivador.
     * 
     * @return FULLNAME
     */
    public java.lang.String getFULLNAME() {
        return FULLNAME;
    }


    /**
     * Sets the FULLNAME value for this InsertUsuarioLdapActivador.
     * 
     * @param FULLNAME
     */
    public void setFULLNAME(java.lang.String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }


    /**
     * Gets the EMAIL value for this InsertUsuarioLdapActivador.
     * 
     * @return EMAIL
     */
    public java.lang.String getEMAIL() {
        return EMAIL;
    }


    /**
     * Sets the EMAIL value for this InsertUsuarioLdapActivador.
     * 
     * @param EMAIL
     */
    public void setEMAIL(java.lang.String EMAIL) {
        this.EMAIL = EMAIL;
    }


    /**
     * Gets the PHONE value for this InsertUsuarioLdapActivador.
     * 
     * @return PHONE
     */
    public java.lang.String getPHONE() {
        return PHONE;
    }


    /**
     * Sets the PHONE value for this InsertUsuarioLdapActivador.
     * 
     * @param PHONE
     */
    public void setPHONE(java.lang.String PHONE) {
        this.PHONE = PHONE;
    }


    /**
     * Gets the VENDOR_DOC_NUMBER value for this InsertUsuarioLdapActivador.
     * 
     * @return VENDOR_DOC_NUMBER
     */
    public java.lang.String getVENDOR_DOC_NUMBER() {
        return VENDOR_DOC_NUMBER;
    }


    /**
     * Sets the VENDOR_DOC_NUMBER value for this InsertUsuarioLdapActivador.
     * 
     * @param VENDOR_DOC_NUMBER
     */
    public void setVENDOR_DOC_NUMBER(java.lang.String VENDOR_DOC_NUMBER) {
        this.VENDOR_DOC_NUMBER = VENDOR_DOC_NUMBER;
    }


    /**
     * Gets the ROL value for this InsertUsuarioLdapActivador.
     * 
     * @return ROL
     */
    public java.lang.String getROL() {
        return ROL;
    }


    /**
     * Sets the ROL value for this InsertUsuarioLdapActivador.
     * 
     * @param ROL
     */
    public void setROL(java.lang.String ROL) {
        this.ROL = ROL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertUsuarioLdapActivador)) return false;
        InsertUsuarioLdapActivador other = (InsertUsuarioLdapActivador) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME()))) &&
            ((this.FULLNAME==null && other.getFULLNAME()==null) || 
             (this.FULLNAME!=null &&
              this.FULLNAME.equals(other.getFULLNAME()))) &&
            ((this.EMAIL==null && other.getEMAIL()==null) || 
             (this.EMAIL!=null &&
              this.EMAIL.equals(other.getEMAIL()))) &&
            ((this.PHONE==null && other.getPHONE()==null) || 
             (this.PHONE!=null &&
              this.PHONE.equals(other.getPHONE()))) &&
            ((this.VENDOR_DOC_NUMBER==null && other.getVENDOR_DOC_NUMBER()==null) || 
             (this.VENDOR_DOC_NUMBER!=null &&
              this.VENDOR_DOC_NUMBER.equals(other.getVENDOR_DOC_NUMBER()))) &&
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
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        if (getFULLNAME() != null) {
            _hashCode += getFULLNAME().hashCode();
        }
        if (getEMAIL() != null) {
            _hashCode += getEMAIL().hashCode();
        }
        if (getPHONE() != null) {
            _hashCode += getPHONE().hashCode();
        }
        if (getVENDOR_DOC_NUMBER() != null) {
            _hashCode += getVENDOR_DOC_NUMBER().hashCode();
        }
        if (getROL() != null) {
            _hashCode += getROL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertUsuarioLdapActivador.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "InsertUsuarioLdapActivador"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USERNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FULLNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FULLNAME"));
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
        elemField.setFieldName("PHONE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PHONE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VENDOR_DOC_NUMBER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VENDOR_DOC_NUMBER"));
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
