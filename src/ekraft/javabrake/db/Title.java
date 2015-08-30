package ekraft.javabrake.db;


import lombok.Data;

import java.util.List;


/**
 * Created by ekraft on 8/29/15
 */

@Data
public class Title {

  private int index;
  private int duration;
  private int angles = 1;
  private boolean mainFeature = false;

  private List<Integer> chapters;
  private List<AudioTrack> audioTracks;
  private List<String> subtitleTracks;
}
