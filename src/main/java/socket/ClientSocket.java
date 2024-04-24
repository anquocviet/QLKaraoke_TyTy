package socket;

import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @description
 * @author: vie
 * @date: 12/4/24
 */
public class ClientSocket {
   private final String host = "localhost";
   @Getter
   private static Socket socket;
   @Getter
   private static DataInputStream dis;
   @Getter
   private static DataOutputStream dos;
   @Getter
   private static ObjectInputStream in;
   @Getter
   private static ObjectOutputStream out;

   public void start() {
      try {
         socket = new Socket(host, 8080);
         System.out.println("Connected to server");

         dos = new DataOutputStream(socket.getOutputStream());
         dis = new DataInputStream(socket.getInputStream());
         out = new ObjectOutputStream(socket.getOutputStream());
         in = new ObjectInputStream(socket.getInputStream());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
