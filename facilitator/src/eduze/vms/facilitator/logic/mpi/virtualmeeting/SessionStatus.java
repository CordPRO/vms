/**
 * SessionStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eduze.vms.facilitator.logic.mpi.virtualmeeting;

public class SessionStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SessionStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NotReady = "NotReady";
    public static final java.lang.String _WaitingForFirstFacilitator = "WaitingForFirstFacilitator";
    public static final java.lang.String _WaitingForSecondFacilitator = "WaitingForSecondFacilitator";
    public static final java.lang.String _MeetingOnline = "MeetingOnline";
    public static final java.lang.String _Adjourned = "Adjourned";
    public static final SessionStatus NotReady = new SessionStatus(_NotReady);
    public static final SessionStatus WaitingForFirstFacilitator = new SessionStatus(_WaitingForFirstFacilitator);
    public static final SessionStatus WaitingForSecondFacilitator = new SessionStatus(_WaitingForSecondFacilitator);
    public static final SessionStatus MeetingOnline = new SessionStatus(_MeetingOnline);
    public static final SessionStatus Adjourned = new SessionStatus(_Adjourned);
    public java.lang.String getValue() { return _value_;}
    public static SessionStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SessionStatus enumeration = (SessionStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SessionStatus fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SessionStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.logic.server.vms.eduze/", "sessionStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
