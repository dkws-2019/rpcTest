package client.liuchao;

import entity.RequestData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcClient {

    public static <T> T  getProxy(final Class<T> interfaceClass, final InetSocketAddress inetSocketAddress){

        Object o = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RequestData requestData=new RequestData();
                String name = interfaceClass.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                requestData.setInterfaceName(name);
                requestData.setMethodName(methodName);
                requestData.setParamaters(args);
                requestData.setParamaterTypes(parameterTypes);

                Socket socket=new Socket();
                socket.connect(inetSocketAddress);
                ObjectOutputStream objectOutputStream=null;
                ObjectInputStream objectInputStream=null;
               try {
                    objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                    objectInputStream=new ObjectInputStream(socket.getInputStream());
                   objectOutputStream.writeObject(requestData);
                   Object o1 = objectInputStream.readObject();
              return o1;

               }catch (Exception e){
                   e.printStackTrace();
               }finally {
                   objectOutputStream.flush();
                   objectOutputStream.close();
                   objectInputStream.close();
               }
                return null;
            }
        });
        return (T)o;
    }
}
