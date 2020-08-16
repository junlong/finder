package com.github.junlong.cmd;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * The cmd execute context.
 *
 * @author junlong
 * @version : Context.java, v 0.1 2020年08月11日 8:12 PM junlong Exp $
 */
public class Context {
  /** cmd options */
  private Option[]   options;
  /** target files */
  private List<File> fileList;
  /**
   * get option by opt
   *
   * @param opt the opt
   * @return option option
   */
  public Option getOption(String opt) {
    if (this.options == null || this.options.length == 0) {
      return null;
    }

    for (Option option : options) {
      if (StringUtils.equals(option.getOpt(), opt)) {
        return option;
      }
    }

    return null;
  }

  /**
   * Get options option [ ].
   *
   * @return the option [ ]
   */
  public Option[] getOptions() {
    return options;
  }

  /**
   * Sets options.
   *
   * @param options the options
   */
  public void setOptions(Option[] options) {
    this.options = options;
  }

  /**
   * Gets file list.
   *
   * @return the file list
   */
  public List<File> getFileList() {
    return fileList;
  }

  /**
   * Sets file list.
   *
   * @param fileList the file list
   */
  public void setFileList(List<File> fileList) {
    this.fileList = fileList;
  }
}
