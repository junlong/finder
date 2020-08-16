package com.github.junlong.cmd;

import com.github.junlong.util.LogUtil;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author junlong
 * @version : DeleteCmd.java, v 0.1 2020年08月11日 9:31 PM junlong Exp $
 */
public class DeleteCmd extends AbstractCmd implements Cmd {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCmd.class);

  private static Option delete =
      Option.builder("d").longOpt("delete").desc("delete the target file.").build();

  @Override
  public void execute(Context context) {
    final Option option = context.getOption(delete.getOpt());
    if (option == null || context.getFileList() == null || context.getFileList().size() == 0) {
      return;
    }

    for (File file : context.getFileList()) {
      boolean result = file.delete();
      if (!result) {
        LogUtil.error(LOGGER, String.format("fail to delete file,src=%s", file.getAbsolutePath()));
        return;
      }

      LogUtil.info(LOGGER, "delete file,src=" + file.getAbsolutePath());
    }
  }

  @Override
  public Option[] options() {
    return new Option[] {delete};
  }
}
