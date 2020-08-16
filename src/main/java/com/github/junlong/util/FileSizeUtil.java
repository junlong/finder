package com.github.junlong.util;

/**
 * file size util
 *
 * @author junlong
 * @version : FileSizeUtil.java, v 0.1 2020年08月11日 9:28 PM junlong Exp $
 */
public class FileSizeUtil {
  static final long GB = 1024 * 1024 * 1024;
  static final long MB = 1024 * 1024;
  static final long KB = 1024;

  /**
   * convert to readable size
   *
   * @param length
   * @return
   */
  public static String readableSize(long length) {
    String size = "";
    if (length >= GB) {
      size = String.format("%sG", length / GB);
    } else if (length >= MB) {
      size = String.format("%sM", length / MB);
    } else if (length >= KB) {
      size = String.format("%sK", length / KB);
    } else {
      size = String.format("%sB", length);
    }

    return size;
  }
}
