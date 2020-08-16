package com.github.junlong.cmd;

import com.github.junlong.util.LogUtil;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * find file cmd
 *
 * @author junlong
 * @version : FindCmd.java, v 0.1 2020年08月11日 9:52 PM junlong Exp $
 */
public class FindCmd implements Cmd {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindCmd.class);
  /** path option */
  private static final Option path =
      Option.builder("p")
          .longOpt("path")
          .argName("path")
          .numberOfArgs(1)
          .hasArg(true)
          .desc("the particular path to find target file.")
          .build();

  /** regex option */
  private static Option expression =
      Option.builder("e")
          .longOpt("expression")
          .argName("expression")
          .numberOfArgs(1)
          .hasArg(true)
          .optionalArg(true)
          .desc("regex to match the filename.")
          .build();

  /** regex pattern */
  private Pattern pattern;
  /** regex expression */
  private String regexStr;

  @Override
  public void execute(Context context) {
    Option pathOpt = context.getOption(path.getOpt());
    if (pathOpt == null || StringUtils.isBlank(pathOpt.getValue())) {
      throw new IllegalArgumentException("illegal option!");
    }

    File file = new File(StringUtils.trim(pathOpt.getValue()));
    if (!file.exists()) {
      LogUtil.error(LOGGER, "path=%s not exist!", pathOpt.getValue());
      return;
    }

    Option expOpt = context.getOption(expression.getOpt());
    if (expOpt != null) {
      String str = StringUtils.trim(expOpt.getValue());
      if (StringUtils.isNotBlank(str) && !StringUtils.equals(str, regexStr)) {
        try {
          pattern = Pattern.compile(str);
          regexStr = StringUtils.trim(str);
        } catch (Throwable e) {
          LogUtil.error(LOGGER, e, "illegal regex=%s", regexStr);
          return;
        }
      }
    }

    List<File> fileList = new LinkedList<File>();
    listFiles(file, fileList);
    context.setFileList(fileList);
  }

  /**
   * list files
   *
   * @param f
   * @param files
   */
  private void listFiles(File f, List<File> files) {
    if (f.isDirectory()) {
      // list files
      File[] list = f.listFiles();
      for (File t : list) {
        listFiles(t, files);
      }
      return;
    }

    if (pattern == null) {
      files.add(f);
      return;
    }

    Matcher m = pattern.matcher(f.getName());
    if (m.find()) {
      files.add(f);
    } else {
      LogUtil.debug(LOGGER, "regex,skip file src=%s", f.getAbsolutePath());
    }
  }

  @Override
  public Option[] options() {
    return new Option[] {path, expression};
  }
}
