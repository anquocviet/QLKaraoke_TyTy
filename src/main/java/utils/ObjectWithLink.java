package utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @author: vie
 * @date: 17/4/24
 */

@Getter
@Setter
@NoArgsConstructor
public class ObjectWithLink {
   private String link;
   private Object object;

   public ObjectWithLink(String link, Object object) {
      this.link = link;
      this.object = object;
   }
}
