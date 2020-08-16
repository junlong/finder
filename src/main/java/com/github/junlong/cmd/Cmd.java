package com.github.junlong.cmd;

import org.apache.commons.cli.Option;

/**
 * cmd
 *
 * @author junlong
 * @version : Cmd.java, v 0.1 2020年08月11日 7:50 PM junlong Exp $
 */
public interface Cmd {
  /**
   * execute cmd
   *
   * @param context execute context
   */
  void execute(Context context);

  /**
   * correspond options
   *
   * @return
   */
  Option[] options();
}
