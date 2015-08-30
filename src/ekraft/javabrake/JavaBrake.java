package ekraft.javabrake;


import ekraft.javabrake.db.AudioTrack;
import ekraft.javabrake.db.Chapter;
import ekraft.javabrake.db.Disc;
import ekraft.javabrake.db.SubtitleTrack;
import ekraft.javabrake.db.Title;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ekraft.javabrake.utils.JavaBrakeUtils.durationToSeconds;
import static ekraft.javabrake.utils.JavaBrakeUtils.match;
import static ekraft.javabrake.utils.JavaBrakeUtils.verifyIncrementStartingWith;


/**
 * Created by ekraft on 8/29/15
 */
public class JavaBrake {

  private static final String TITLE_START = "^\\+ title (\\d+):$";
  private static final String TITLE_LINE = "^  \\+ ([A-Za-z ]+)";
  private static final String DURATION_LINE = "duration: (\\d{2}:\\d{2}:\\d{2})";
  private static final String ANGLES_LINE = "angle\\(s\\) (\\d+)";
  private static final String CHAPTER_LINE = "    \\+ (\\d+): cells \\d+->\\d+, \\d+ blocks, duration (\\d{2}:\\d{2}:\\d{2})";
  private static final String AUDIO_LINE = "    \\+ (\\d+), (\\w+) .* \\((\\d+.\\d+) ch\\) .*, \\d+Hz, \\d+bps";
  private static final String SUBTITLE_LINE = "    \\+ (\\d+), ([A-Za-z ]+) .*";

  private static final Pattern TITLE_START_PATTERN = Pattern.compile(TITLE_START);
  private static final Pattern TITLE_LINE_PATTERN = Pattern.compile(TITLE_LINE);
  private static final Pattern DURATION_LINE_PATTERN = Pattern.compile(DURATION_LINE);
  private static final Pattern ANGLES_LINE_PATTERN = Pattern.compile(ANGLES_LINE);
  private static final Pattern CHAPTER_LINE_PATTERN = Pattern.compile(CHAPTER_LINE);
  private static final Pattern AUDIO_LINE_PATTERN = Pattern.compile(AUDIO_LINE);
  private static final Pattern SUBTITLE_LINE_PATTERN = Pattern.compile(SUBTITLE_LINE);


  public static Disc convertToDisc(String name,
                                   String output) {

    return convertToDisc(name, new LinkedList<>(Arrays.asList(output.split("\n"))));
  }


  public static Disc convertToDisc(String name,
                                   Queue<String> output) {

    Disc disc = new Disc();
    disc.setName(name);

    List<Title> titles = new ArrayList<>();
    disc.setTitles(titles);

    while (output.size() > 0) {
      Title title = readTitle(output);
      if (title == null) {
        output.poll();
      } else {
        titles.add(title);
      }
    }

    return disc;
  }


  private static Title readTitle(Queue<String> output) {

    String[] titleMatch = match(output.poll(), TITLE_START_PATTERN);
    if (titleMatch == null || titleMatch.length == 0) {
      return null;
    }

    Title title = new Title();
    title.setIndex(Integer.parseInt(titleMatch[0]));

    while (output.size() > 0) {
      if (!output.peek().startsWith("  + ")) {
        break;
      }

      String line = output.poll();

      String[] titleLine = match(line, TITLE_LINE_PATTERN);
      if (titleLine == null || titleLine.length == 0) {
        continue;
      }

      switch (titleLine[0]) {
        case "Main Feature":
          title.setMainFeature(true);
          break;
        case "duration":
          title.setDuration(durationToSeconds(match(line, DURATION_LINE_PATTERN, "00:00:00")[0]));
          break;
        case "angle":
          title.setAngles(Integer.parseInt(match(line, ANGLES_LINE_PATTERN, "1")[0]));
          break;
        case "chapters":
          title.setChapters(readChapters(output));
          break;
        case "audio tracks":
          title.setAudioTracks(readAudioTracks(output));
          break;
        case "subtitle tracks":
          title.setSubtitleTracks(readSubtitleTracks(output));
          break;
      }
    }

    return title;
  }


  // Since for now I only care about duration, simplify to just integers.
  private static List<Integer> readChapters(Queue<String> output) {

    List<Chapter> chapters = readChaptersComplex(output);
    verifyIncrementStartingWith(chapters, Chapter::getIndex, "Chapters", 1);
    return chapters.stream().map(Chapter::getDuration).collect(Collectors.toList());
  }


  private static List<Chapter> readChaptersComplex(Queue<String> output) {

    List<Chapter> results = new ArrayList<>();

    while (output.size() > 0) {
      String[] matches = match(output.peek(), CHAPTER_LINE_PATTERN);
      if (matches == null) {
        break;
      }

      output.poll();

      Chapter chapter = new Chapter();
      chapter.setIndex(Integer.parseInt(matches[0]));
      chapter.setDuration(durationToSeconds(matches[1]));
      results.add(chapter);
    }

    return results;
  }


  private static List<AudioTrack> readAudioTracks(Queue<String> output) {

    List<AudioTrack> results = new ArrayList<>();

    while (output.size() > 0) {
      String[] matches = match(output.peek(), AUDIO_LINE_PATTERN);
      if (matches == null) {
        break;
      }

      output.poll();

      AudioTrack audioTrack = new AudioTrack();
      audioTrack.setIndex(Integer.parseInt(matches[0]));
      audioTrack.setLanguage(matches[1]);
      audioTrack.setChannels(Double.parseDouble(matches[2]));
      results.add(audioTrack);
    }

    return results;
  }


  // Since I currently only want the Language simplify to list of Strings.
  private static List<String> readSubtitleTracks(Queue<String> output) {

    List<SubtitleTrack> subtitleTracks = readSubtitleTracksComplex(output);
    verifyIncrementStartingWith(subtitleTracks, SubtitleTrack::getIndex, "Subtitle Tracks", 1);
    return subtitleTracks.stream().map(SubtitleTrack::getLanguage).collect(Collectors.toList());
  }


  private static List<SubtitleTrack> readSubtitleTracksComplex(Queue<String> output) {

    List<SubtitleTrack> results = new ArrayList<>();

    while (output.size() > 0) {
      String[] matches = match(output.peek(), SUBTITLE_LINE_PATTERN);
      if (matches == null) {
        break;
      }

      output.poll();

      SubtitleTrack subtitleTrack = new SubtitleTrack();
      subtitleTrack.setIndex(Integer.parseInt(matches[0]));
      subtitleTrack.setLanguage(matches[1]);
      results.add(subtitleTrack);
    }

    return results;
  }
}
