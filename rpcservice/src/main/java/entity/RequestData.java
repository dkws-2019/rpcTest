package entity;

import java.io.Serializable;

public class RequestData implements Serializable {
    private String interfaceName;
    private Class<?> paramaterTypes[];
    private  Object paramaters[];
    private String methodName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class<?>[] getParamaterTypes() {
        return paramaterTypes;
    }

    public void setParamaterTypes(Class<?>[] paramaterTypes) {
        this.paramaterTypes = paramaterTypes;
    }

    public Object[] getParamaters() {
        return paramaters;
    }

    public void setParamaters(Object[] paramaters) {
        this.paramaters = paramaters;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
