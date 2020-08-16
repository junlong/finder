package com.github.junlong.cmd;

import com.github.junlong.util.LogUtil;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author junlong
 * @version : CopyCmd.java, v 0.1 2020年08月11日 8:37 PM junlong Exp $
 */
public class CopyCmd extends AbstractCmd implements Cmd {
  private static final Logger LOGGER = LoggerFactory.getLogger(CopyCmd.class);

  private static final Option copy =
      Option.builder("c")
          .longOpt("copy")
          .argName("path")
          .numberOfArgs(1)
          .hasArg(true)
          .desc(
              "copy the target file to the particular path. copy will be skipped when the target file already exists in the particular path.")
          .build();

  @Override
  public void execute(Context context) {
    execute(
        context,
        context.getOption(copy.getOpt()),
        new OperateCallback() {
          @Override
          public void operate(File src, File dest) {
            try {
              FileInputStream fis = new FileInputStream(src.getPath());
              FileOutputStream fos = new FileOutputStream(dest.getPath());
              byte[] data = new byte[1024 * 8];
              int len = 0;
              while ((len = fis.read(data)) != -1) {
                fos.write(data, 0, len);
              }
              fis.close();
              fis.close();
            } catch (Exception e) {
              LogUtil.error(
                  LOGGER,
                  e,
                  "error occurs when copy files.src=%s,dest=%s",
                  src.getAbsolutePath(),
                  dest.getAbsolutePath());
            }
          }

          @Override
          public String name() {
            return copy.getLongOpt();
          }
        });
  }

  @Override
  public Option[] options() {
    return new Option[] {copy};
  }
}
