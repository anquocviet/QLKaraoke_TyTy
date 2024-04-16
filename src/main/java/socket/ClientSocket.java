package socket;

import lombok.Getter;

import java.net.Socket;
import java.util.Scanner;

/**
 * @description
 * @author: vie
 * @date: 12/4/24
 */
public class ClientSocket {
   @Getter
   public static Socket socket;
   private final String host = "localhost";

   public void start() {
      try {
         socket = new Socket(host, 8080);
         Scanner sc = new Scanner(System.in);
         System.out.println("Connected to server");

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
