/**
 * InsertLuckyNumbers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public class InsertLuckyNumbers  implements java.io.Serializable {
    private java.lang.String PERFIL;

    private java.lang.String EMAIL;

    private java.lang.String LOGIN;

    public InsertLuckyNumbers() {
    }

    public InsertLuckyNumbers(
           java.lang.String PERFIL,
           java.lang.String EMAIL,
           java.lang.String LOGIN) {
           this.PERFIL = PERFIL;
           this.EMAIL = EMAIL;
           this.LOGIN = LOGIN;
    }


    /**
     * Gets the PERFIL value for this InsertLuckyNumbers.
     * 
     * @return PERFIL
     */
    public java.lang.String getPERFIL() {
        return PERFIL;
    }


    /**
     * Sets the PERFIL value for this InsertLuckyNumbers.
     * 
     * @param PERFIL
     */
    public void setPERFIL(java.lang.String PERFIL) {
        this.PERFIL = PERFIL;
    }


    /**
     * Gets the EMAIL value for this InsertLuckyNumbers.
     * 
     * @return EMAIL
     */
    public java.lang.String getEMAIL() {
        return EMAIL;
    }


    /**
     * Sets the EMAIL value for this InsertLuckyNumbers.
     * 
     * @param EMAIL
     */
    public void setEMAIL(java.lang.String EMAIL) {
        this.EMAIL = EMAIL;
    }


    /**
     * Gets the LOGIN value for this InsertLuckyNumbers.
     * 
     * @return LOGIN
     */
    public java.lang.String getLOGIN() {
        return LOGIN;
    }


    /**
     * Sets the LOGIN value for this InsertLuckyNumbers.
     * 
     * @param LOGIN
     */
    public void setLOGIN(java.lang.String LOGIN) {
        this.LOGIN = LOGIN;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsertLuckyNumbers)) return false;
        InsertLuckyNumbers other = (InsertLuckyNumbers) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.PERFIL==null && other.getPERFIL()==null) || 
             (this.PERFIL!=null &&
              this.PERFIL.equals(other.getPERFIL()))) &&
            ((this.EMAIL==null && other.getEMAIL()==null) || 
             (this.EMAIL!=null &&
              this.EMAIL.equals(other.getEMAIL()))) &&
            ((this.LOGIN==null && other.getLOGIN()==null) || 
             (this.LOGIN!=null &&
              this.LOGIN.equals(other.getLOGIN())));
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
        if (getPERFIL() != null) {
            _hashCode += getPERFIL().hashCode();
        }
        if (getEMAIL() != null) {
            _hashCode += getEMAIL().hashCode();
        }
        if (getLOGIN() != null) {
            _hashCode += getLOGIN().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsertLuckyNumbers.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servicios/", "InsertLuckyNumbers"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PERFIL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PERFIL"));
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
        elemField.setFieldName("LOGIN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOGIN"));
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
