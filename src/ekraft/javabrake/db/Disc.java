package ekraft.javabrake.db;


import lombok.Data;

import java.util.List;


/**
 * Created by ekraft on 8/29/15
 */
@Data
public class Disc {
  private String name;
  private boolean dvd = true;
  private List<Title> titles;
}
