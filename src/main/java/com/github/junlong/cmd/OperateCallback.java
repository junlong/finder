package com.github.junlong.cmd;

import java.io.File;

/**
 * file operate callback
 *
 * @author junlong
 * @version : OperateCallback.java, v 0.1 2020年08月10日 8:18 PM junlong Exp $
 */
public interface OperateCallback {
  /**
   * file operate
   *
   * @param src
   * @param dest
   */
  void operate(File src, File dest);

  /**
   * name of operate
   *
   * @return
   */
  String name();
}
