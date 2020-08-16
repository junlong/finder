package com.github.junlong.util;

import org.slf4j.Logger;

/**
 * log util
 *
 * @author junlong
 * @version : LogUtil.java, v 0.1 2020年08月11日 8:10 PM junlong Exp $
 */
public class LogUtil {
  /**
   * debug
   *
   * @param logger
   * @param fmt
   * @param args
   */
  public static void debug(Logger logger, String fmt, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(fmt, args));
    }
  }

  /**
   * info
   *
   * @param logger
   * @param fmt
   * @param args
   */
  public static void info(Logger logger, String fmt, Object... args) {
    if (logger.isInfoEnabled()) {
      logger.info(String.format(fmt, args));
    }
  }

  /**
   * warn
   *
   * @param logger
   * @param fmt
   * @param args
   */
  public static void warn(Logger logger, String fmt, Object... args) {
    if (logger.isWarnEnabled()) {
      logger.warn(String.format(fmt, args));
    }
  }

  /**
   * error
   *
   * @param logger
   * @param fmt
   * @param args
   */
  public static void error(Logger logger, String fmt, Object... args) {
    if (logger.isErrorEnabled()) {
      logger.error(String.format(fmt, args));
    }
  }

  /**
   * error message
   *
   * @param e
   * @param fmt
   * @param args
   */
  public static void error(Logger logger, Throwable e, String fmt, Object... args) {
    if (logger.isErrorEnabled()) {
      logger.error(String.format(fmt, args), e);
    }
  }
}
