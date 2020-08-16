package com.github.junlong.cmd;

import com.github.junlong.util.LogUtil;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author junlong
 * @version : AbstractCmd.java, v 0.1 2020年08月11日 8:53 PM junlong Exp $
 */
public abstract class AbstractCmd {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCmd.class);
  /**
   * operate files
   *
   * @param file
   * @param destPath
   */
  protected void operate(File file, String destPath, OperateCallback callback) {
    if (StringUtils.isBlank(destPath)) {
      LogUtil.error(LOGGER, "dest path is empty");
      return;
    }

    File dest = new File(StringUtils.trim(destPath));
    if (!dest.isDirectory()) {
      LogUtil.error(LOGGER, "destination path is not directory,dest=" + destPath);
      return;
    }

    File target = new File(dest, file.getName());
    if (!target.exists()) {
      callback.operate(file, target);
    } else {
      LogUtil.info(
          LOGGER,
          String.format(
              "%s file is skipped,src=%s,dest=%s",
              callback.name(), file.getAbsolutePath(), target.getAbsolutePath()));
    }

    return;
  }

  /**
   * template
   *
   * @param context
   * @param option
   * @param callback
   */
  protected void execute(Context context, Option option, OperateCallback callback) {
    if (option == null || context.getFileList() == null || context.getFileList().size() == 0) {
      return;
    }

    for (File file : context.getFileList()) {
      if (!file.exists()) {
        LogUtil.info(LOGGER, "file is not exist,path=" + file.getAbsolutePath());
        continue;
      }
      operate(file, option.getValue(), callback);
    }
  }
}
