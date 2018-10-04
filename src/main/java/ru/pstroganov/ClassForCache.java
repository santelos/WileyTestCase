/*
 * Created by PStroganov 04/10/18 00:10
 */

package ru.pstroganov;

import java.io.Serializable;

public class ClassForCache implements Serializable {

    ClassForCache(){
        this(15,"asd",56D);
    }

    public ClassForCache(Integer value, String strValue, Double doubleValue) {
        this.value = value;
        this.strValue = strValue;
        this.doubleValue = doubleValue;
    }

    Integer value;

    String strValue;

    Double doubleValue;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public String toString() {
        return "[" + value + " " + strValue + " " + doubleValue + "]";
    }
}
