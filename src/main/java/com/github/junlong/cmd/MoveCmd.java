package com.github.junlong.cmd;

import com.github.junlong.util.LogUtil;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author junlong
 * @version : MoveCmd.java, v 0.1 2020年08月11日 9:37 PM junlong Exp $
 */
public class MoveCmd extends AbstractCmd implements Cmd {
  private static final Logger LOGGER = LoggerFactory.getLogger(MoveCmd.class);

  private static final Option move =
      new Option(
          "m",
          "move",
          true,
          "move the target file to the particular path. Move will be skipped when the target file already exists in the particular path.");

  static {
    move.setArgName("path");
  }

  @Override
  public void execute(Context context) {
    execute(
        context,
        context.getOption(move.getOpt()),
        new OperateCallback() {
          @Override
          public void operate(File src, File dest) {
            boolean result = src.renameTo(dest);
            if (!result) {
              LogUtil.error(
                  LOGGER,
                  String.format(
                      "fail to move file,src=%s,dest=%s",
                      src.getAbsolutePath(), dest.getAbsolutePath()));
              return;
            }

            LogUtil.info(
                LOGGER,
                String.format(
                    "move file,src=%s,dest=%s", src.getAbsolutePath(), dest.getAbsolutePath()));
          }

          @Override
          public String name() {
            return move.getLongOpt();
          }
        });
  }

  @Override
  public Option[] options() {
    return new Option[] {move};
  }
}
