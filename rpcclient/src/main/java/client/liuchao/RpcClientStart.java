package client.liuchao;

import service.UserService;

import java.net.InetSocketAddress;

public class RpcClientStart {
    public static void main(String[] args) {
        UserService userService = RpcClient.getProxy(UserService.class, new InetSocketAddress("localhost", 21881));
        String str = userService.sayHellow("张三");
        System.out.println(str);
    }
}
