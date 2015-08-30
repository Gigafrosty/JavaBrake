package ekraft.javabrake;


import ekraft.javabrake.db.Disc;
import ekraft.javabrake.db.Title;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


/**
 * Created by ekraft on 8/30/15
 */
public class JavaBrakeTest {

  @Test
  public void testSwordArtOnline2Volume1Disc1() {

    Disc disc = JavaBrake.convertToDisc("Sword Art Online 2 Volume 1 Disc 1", "+ title 1:\n" +
      "  + Main Feature\n" +
      "  + vts 1, ttn 1, cells 0->17 (2014372 blocks)\n" +
      "  + duration: 01:11:07\n" +
      "  + size: 720x480, pixel aspect: 32/27, display aspect: 1.78, 23.976 fps\n" +
      "  + autocrop: 0/0/0/0\n" +
      "  + support opencl: no\n" +
      "  + support hwd: not built-in\n" +
      "  + chapters:\n" +
      "    + 1: cells 0->0, 100610 blocks, duration 00:03:22\n" +
      "    + 2: cells 1->1, 145020 blocks, duration 00:05:06\n" +
      "    + 3: cells 2->2, 379884 blocks, duration 00:13:36\n" +
      "    + 4: cells 3->3, 45528 blocks, duration 00:01:30\n" +
      "    + 5: cells 4->4, 3152 blocks, duration 00:00:07\n" +
      "    + 6: cells 5->5, 52560 blocks, duration 00:01:53\n" +
      "    + 7: cells 6->6, 44297 blocks, duration 00:01:30\n" +
      "    + 8: cells 7->7, 255265 blocks, duration 00:09:07\n" +
      "    + 9: cells 8->8, 270449 blocks, duration 00:09:34\n" +
      "    + 10: cells 9->9, 46850 blocks, duration 00:01:30\n" +
      "    + 11: cells 10->10, 3122 blocks, duration 00:00:07\n" +
      "    + 12: cells 11->11, 12291 blocks, duration 00:00:27\n" +
      "    + 13: cells 12->12, 44428 blocks, duration 00:01:30\n" +
      "    + 14: cells 13->13, 332539 blocks, duration 00:12:01\n" +
      "    + 15: cells 14->14, 225660 blocks, duration 00:08:06\n" +
      "    + 16: cells 15->15, 46858 blocks, duration 00:01:30\n" +
      "    + 17: cells 16->17, 5859 blocks, duration 00:00:13\n" +
      "  + audio tracks:\n" +
      "    + 1, English (AC3) (2.0 ch) (iso639-2: eng), 48000Hz, 384000bps\n" +
      "    + 2, Japanese (AC3) (2.0 ch) (iso639-2: jpn), 48000Hz, 384000bps\n" +
      "  + subtitle tracks:\n" +
      "    + 1, English (iso639-2: eng) (Bitmap)(VOBSUB)\n" +
      "    + 2, English (iso639-2: eng) (Bitmap)(VOBSUB)\n" +
      "    + 3, Espanol (iso639-2: spa) (Bitmap)(VOBSUB)\n" +
      "+ title 2:\n" +
      "  + vts 2, ttn 1, cells 0->0 (43585 blocks)\n" +
      "  + duration: 00:01:32\n" +
      "  + size: 720x480, pixel aspect: 32/27, display aspect: 1.78, 23.976 fps\n" +
      "  + autocrop: 0/0/0/0\n" +
      "  + support opencl: no\n" +
      "  + support hwd: not built-in\n" +
      "  + chapters:\n" +
      "    + 1: cells 0->0, 43585 blocks, duration 00:01:32\n" +
      "  + audio tracks:\n" +
      "    + 1, Japanese (AC3) (2.0 ch) (iso639-2: jpn), 48000Hz, 384000bps\n" +
      "  + subtitle tracks:\n" +
      "\n");

    assertEquals("Sword Art Online 2 Volume 1 Disc 1", disc.getName());
    assertTrue(disc.isDvd());
    assertEquals(2, disc.getTitles().size());

    Title title1 = disc.getTitles().get(0);
    assertEquals(1, title1.getIndex());
    assertEquals(1, title1.getAngles());
    assertTrue(title1.isMainFeature());
    assertEquals(4267, title1.getDuration());
    assertEquals(Arrays.asList(202, 306, 816, 90, 7, 113, 90, 547, 574, 90, 7, 27, 90, 721, 486, 90, 13),
      title1.getChapters());
    assertEquals(Arrays.asList("English", "English", "Espanol"), title1.getSubtitleTracks());
    assertEquals(2, title1.getAudioTracks().size());
    assertEquals(1, title1.getAudioTracks().get(0).getIndex());
    assertEquals("English", title1.getAudioTracks().get(0).getLanguage());
    assertEquals(2.0, title1.getAudioTracks().get(0).getChannels());
    assertEquals(2, title1.getAudioTracks().get(1).getIndex());
    assertEquals("Japanese", title1.getAudioTracks().get(1).getLanguage());
    assertEquals(2.0, title1.getAudioTracks().get(1).getChannels());


    Title title2 = disc.getTitles().get(1);
    assertEquals(2, title2.getIndex());
    assertEquals(1, title2.getAngles());
    assertFalse(title2.isMainFeature());
    assertEquals(92, title2.getDuration());
    assertEquals(Collections.singletonList(92), title2.getChapters());
    assertEquals(new ArrayList<>(), title2.getSubtitleTracks());
    assertEquals(1, title2.getAudioTracks().size());
    assertEquals(1, title2.getAudioTracks().get(0).getIndex());
    assertEquals("Japanese", title2.getAudioTracks().get(0).getLanguage());
    assertEquals(2.0, title2.getAudioTracks().get(0).getChannels());
  }


  @Test
  public void test20MillionMilesToEarthDisc1() {

    Disc disc = JavaBrake.convertToDisc("20 Million Miles to Earth (Disc 1)", "+ title 2:\n" +
      "  + vts 2, ttn 1, cells 0->32 (3321668 blocks)\n" +
      "  + angle(s) 2\n" +
      "  + duration: 01:22:27\n" +
      "  + size: 720x480, pixel aspect: 32/27, display aspect: 1.78, 29.970 fps\n" +
      "  + autocrop: 6/10/0/0\n" +
      "  + support opencl: no\n" +
      "  + support hwd: not built-in\n" +
      "  + chapters:\n" +
      "    + 1: cells 0->1, 171664 blocks, duration 00:03:38\n" +
      "    + 2: cells 2->3, 271740 blocks, duration 00:05:47\n" +
      "    + 3: cells 4->5, 327567 blocks, duration 00:08:17\n" +
      "    + 4: cells 6->7, 163747 blocks, duration 00:04:22\n" +
      "    + 5: cells 8->9, 186870 blocks, duration 00:04:57\n" +
      "    + 6: cells 10->11, 178400 blocks, duration 00:04:50\n" +
      "    + 7: cells 12->13, 204418 blocks, duration 00:05:31\n" +
      "    + 8: cells 14->15, 297170 blocks, duration 00:07:49\n" +
      "    + 9: cells 16->17, 171401 blocks, duration 00:04:50\n" +
      "    + 10: cells 18->19, 317947 blocks, duration 00:06:51\n" +
      "    + 11: cells 20->21, 244054 blocks, duration 00:06:56\n" +
      "    + 12: cells 22->23, 169773 blocks, duration 00:04:10\n" +
      "    + 13: cells 24->25, 152584 blocks, duration 00:03:34\n" +
      "    + 14: cells 26->27, 117534 blocks, duration 00:02:51\n" +
      "    + 15: cells 28->29, 176722 blocks, duration 00:04:13\n" +
      "    + 16: cells 30->31, 169323 blocks, duration 00:03:50\n" +
      "    + 17: cells 32->32, 754 blocks, duration 00:00:02\n" +
      "  + audio tracks:\n" +
      "    + 1, English (AC3) (2.0 ch) (iso639-2: eng), 48000Hz, 192000bps\n" +
      "    + 2, English (AC3) (Director's Commentary 1) (2.0 ch) (iso639-2: eng), 48000Hz, 192000bps\n" +
      "  + subtitle tracks:\n" +
      "    + 1, English (iso639-2: eng) (Bitmap)(VOBSUB)\n" +
      "    + 2, Francais (iso639-2: fra) (Bitmap)(VOBSUB)\n" +
      "    + 3, Closed Captions (iso639-2: eng) (Text)(CC)\n" +
      "+ title 5:\n" +
      "  + vts 4, ttn 1, cells 0->0 (4391 blocks)\n" +
      "  + duration: 00:00:14\n" +
      "  + size: 720x480, pixel aspect: 32/27, display aspect: 1.78, 29.970 fps\n" +
      "  + autocrop: 0/2/0/10\n" +
      "  + support opencl: no\n" +
      "  + support hwd: not built-in\n" +
      "  + chapters:\n" +
      "    + 1: cells 0->0, 4204 blocks, duration 00:00:14\n" +
      "    + 2: cells 0->0, 187 blocks, duration 00:00:01\n" +
      "  + audio tracks:\n" +
      "    + 1, Unknown (AC3) (5.1 ch) (iso639-2: und), 48000Hz, 448000bps\n" +
      "  + subtitle tracks:\n" +
      "+ title 10:\n" +
      "  + vts 8, ttn 1, cells 0->1 (5513 blocks)\n" +
      "  + duration: 00:00:17\n" +
      "  + size: 720x480, pixel aspect: 32/27, display aspect: 1.78, 29.970 fps\n" +
      "  + autocrop: 66/72/0/0\n" +
      "  + support opencl: no\n" +
      "  + support hwd: not built-in\n" +
      "  + chapters:\n" +
      "    + 1: cells 0->0, 5326 blocks, duration 00:00:17\n" +
      "    + 2: cells 1->1, 187 blocks, duration 00:00:01\n" +
      "  + audio tracks:\n" +
      "    + 1, Unknown (AC3) (2.0 ch) (iso639-2: und), 48000Hz, 192000bps\n" +
      "  + subtitle tracks:\n");

    assertEquals("20 Million Miles to Earth (Disc 1)", disc.getName());
    assertTrue(disc.isDvd());
    assertEquals(3, disc.getTitles().size());

    Title title1 = disc.getTitles().get(0);
    assertEquals(2, title1.getIndex());
    assertEquals(2, title1.getAngles());
    assertFalse(title1.isMainFeature());
    assertEquals(4947, title1.getDuration());
    assertEquals(Arrays.asList(218, 347, 497, 262, 297, 290, 331, 469, 290, 411, 416, 250, 214, 171, 253, 230, 2),
      title1.getChapters());
    assertEquals(Arrays.asList("English", "Francais", "Closed Captions"), title1.getSubtitleTracks());

    assertEquals(2, title1.getAudioTracks().size());

    assertEquals(1, title1.getAudioTracks().get(0).getIndex());
    assertEquals("English", title1.getAudioTracks().get(0).getLanguage());
    assertEquals(2.0, title1.getAudioTracks().get(0).getChannels());

    assertEquals(2, title1.getAudioTracks().get(1).getIndex());
    assertEquals("English", title1.getAudioTracks().get(1).getLanguage());
    assertEquals(2.0, title1.getAudioTracks().get(1).getChannels());


    Title title2 = disc.getTitles().get(1);
    assertEquals(5, title2.getIndex());
    assertEquals(1, title2.getAngles());
    assertFalse(title2.isMainFeature());
    assertEquals(14, title2.getDuration());
    assertEquals(Arrays.asList(14, 1),
      title2.getChapters());
    assertEquals(new ArrayList<String>(), title2.getSubtitleTracks());
    assertEquals(1, title2.getAudioTracks().size());
    assertEquals(1, title2.getAudioTracks().get(0).getIndex());
    assertEquals("Unknown", title2.getAudioTracks().get(0).getLanguage());
    assertEquals(5.1, title2.getAudioTracks().get(0).getChannels());


    Title title3 = disc.getTitles().get(2);
    assertEquals(10, title3.getIndex());
    assertEquals(1, title3.getAngles());
    assertFalse(title3.isMainFeature());
    assertEquals(17, title3.getDuration());
    assertEquals(Arrays.asList(17, 1), title3.getChapters());
    assertEquals(new ArrayList<String>(), title3.getSubtitleTracks());
    assertEquals(1, title3.getAudioTracks().size());
    assertEquals(1, title3.getAudioTracks().get(0).getIndex());
    assertEquals("Unknown", title3.getAudioTracks().get(0).getLanguage());
    assertEquals(2.0, title3.getAudioTracks().get(0).getChannels());
  }
}