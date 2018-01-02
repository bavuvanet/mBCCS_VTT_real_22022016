package com.viettel.bss.viettelpos.v4.commons;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AttributeObject {
	public List<String> getListAttribute() {
        List<String> properties = new ArrayList<>();
        for (Method method : this.getClass().getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                methodName = methodName.substring(3);

                properties.add(Character.toLowerCase(
              methodName.charAt(0)) + methodName.substring(1));
            }
        }
        return properties;
    }
    public Object getValueOfAttribute(String property) {
        Object returnValue = null;

        try {
            String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
            Class clazz = this.getClass();
            Method method = clazz.getMethod(methodName, null);
            returnValue = method.invoke(this, (Object) null);
        }
        catch (Exception e) {
            // Do nothing, we'll return the default value
        }

        return returnValue;
    }
    public void setValueOfAttribute(String property,Object value) {

        try {
            String methodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
            Class clazz = this.getClass();
            Method method = clazz.getMethod(methodName, value.getClass());
            method.invoke(this, value);
        }
        catch (Exception e) {
            // Do nothing, we'll return the default value
            e.printStackTrace();
        }

    }
}
