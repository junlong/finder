package com.github.junlong;

import com.github.junlong.cmd.Cmd;
import com.github.junlong.cmd.Context;
import com.github.junlong.util.LogUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * finder
 *
 * @author junlong
 * @version : ConsoleFinder.java, v 0.1 2020年08月10日 11:01 PM junlong Exp $
 */
public class ConsoleFinder {
  /** logger instance */
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleFinder.class);
  /** finder instance */
  private static final ConsoleFinder finder = new ConsoleFinder();
  /** cmds conf file name */
  private static final String CMDS_CONF = "cmds.conf";
  /** properties */
  private static final String COMMENT = "#";
  /** cmds */
  private List<Cmd> cmds = new ArrayList<Cmd>();
  /** cmd options */
  private Options options = new Options();

  /** constructor */
  private ConsoleFinder() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(CMDS_CONF);
    if (inputStream == null) {
      throw new IllegalArgumentException(CMDS_CONF + " not found in classpath!");
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    Set<String> cmdSet = new HashSet<String>();
    while (true) {
      try {
        String line = br.readLine();
        // ignore blank line
        if (line == null) {
          break;
        }

        // ignore comments
        if (StringUtils.indexOf(line, "#") == 0) {
          continue;
        }

        String className = StringUtils.trim(line);

        if (cmdSet.contains(className)) {
          LogUtil.warn(LOGGER, "%s already registered!");
          continue;
        }

        // parse cmd
        addCmd(className, classLoader);
      } catch (IOException e) {
        throw new IllegalArgumentException(CMDS_CONF + " is invalid!", e);
      }
    }
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static final ConsoleFinder getInstance() {
    return finder;
  }

  /**
   * finder entry
   *
   * @param args
   */
  public static void main(String[] args) {
    ConsoleFinder finder = ConsoleFinder.getInstance();
    finder.find(args);
  }

  /**
   * add command
   *
   * @param clazzName
   * @param classLoader
   */
  private void addCmd(String clazzName, ClassLoader classLoader) {
    try {
      Class clazz = Class.forName(clazzName, true, classLoader);
      Cmd cmd = (Cmd) clazz.newInstance();
      if (cmd != null && cmd.options() != null) {
        cmds.add(cmd);
        for (Option option : cmd.options()) {
          options.addOption(option);
        }
      }
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException(String.format("%s not found in classpath!", clazzName), e);
    } catch (IllegalAccessException e) {
      throw new IllegalArgumentException(String.format("%s illegal access!", clazzName), e);
    } catch (InstantiationException e) {
      throw new IllegalArgumentException(String.format("%s instantiate error!", clazzName), e);
    }
  }

  /**
   * Find list.
   *
   * @param args the args
   * @return the list
   */
  public void find(String[] args) {
    try {
      CommandLineParser parser = new DefaultParser();
      CommandLine line = parser.parse(options, args);
      if (line.getOptions() == null || line.getOptions().length == 0) {
        help();
        return;
      }

      Context context = new Context();
      context.setOptions(line.getOptions());

      for (Cmd cmd : cmds) {
        cmd.execute(context);
      }

    } catch (ParseException e) {
      LogUtil.error(LOGGER, e, "illegal options");
      help();
    } catch (Throwable e) {
      help();
      LogUtil.error(LOGGER, e, "system error");
    }
  }

  /** print help message */
  private void help() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
        "finder -p [path] [option]",
        "example: finder -p \"/home/admin/logs\" -r \".*\\.log$\" -v\n",
        options,
        "");
  }
}
