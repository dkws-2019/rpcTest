package server;

import server.impl.UserServiceImpl;
import service.UserService;

import java.io.IOException;

public class ServerStart {
    public static void main(String[] args) throws IOException {
        RpcServer rpcServer=new RpcServer(21881);
        rpcServer.pushServer(UserService.class, new UserServiceImpl());
        rpcServer.start();
        System.out.println("端口号为：21881的服务启动成功");
    }
}
