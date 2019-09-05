package server;

import entity.RequestData;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RpcServer {
    private int port;
    private Map<String,Object> map=new HashMap<String,Object>();
    private ThreadPoolExecutor executor=new ThreadPoolExecutor(4,
                20,1000,
            TimeUnit.SECONDS, new ArrayBlockingQueue(1));

    public  RpcServer(int port){
        this.port=port;
    }

    public void pushServer(Class<?> infacerClass,Object instance){
            this.map.put(infacerClass.getName(),instance);
    }

    public void start() throws IOException {
        ServerSocket serverSocket=new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        while (true){
            executor.execute(new serverTask(serverSocket.accept()));
        }

    }

    private class serverTask implements Runnable{
        private Socket socket;
            public serverTask(Socket socket){
                this.socket=socket;
            }
        public void run() {
            ObjectInputStream objectInputStream=null;
            ObjectOutputStream objectOutputStream=null;
            try {
                 objectInputStream = new ObjectInputStream(socket.getInputStream());
                 objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                RequestData data = (RequestData) objectInputStream.readObject();

                String interfaceName = data.getInterfaceName();
                Object instance = map.get(interfaceName);
                String methodName = data.getMethodName();
                Class<?>[] paramaterTypes = data.getParamaterTypes();

                Method declaredMethod = instance.getClass().getDeclaredMethod(methodName, paramaterTypes);
                Object invoke = declaredMethod.invoke(instance, data.getParamaters());
                objectOutputStream.writeObject(invoke);

            }catch (Exception e){
                    e.printStackTrace();
            }finally {
                try {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    objectInputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
};
