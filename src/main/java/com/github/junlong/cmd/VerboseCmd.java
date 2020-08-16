package com.github.junlong.cmd;

import com.github.junlong.util.FileSizeUtil;
import org.apache.commons.cli.Option;

import java.io.File;

/**
 * @author junlong
 * @version : VerboseCmd.java, v 0.1 2020年08月11日 9:42 PM junlong Exp $
 */
public class VerboseCmd implements Cmd {
  private static final Option verbose =
      new Option("v", "verbose", false, "show detail of the file.");

  @Override
  public void execute(Context context) {

    Option option = context.getOption(verbose.getOpt());

    if (option == null || context.getFileList() == null || context.getFileList().size() == 0) {
      return;
    }

    for (File file : context.getFileList()) {
      System.out.println(
          String.format(
              "file=%s,size=%s", file.getAbsolutePath(), FileSizeUtil.readableSize(file.length())));
    }
  }

  @Override
  public Option[] options() {
    return new Option[] {verbose};
  }
}
