package com.github.junlong;

import org.junit.Test;

/**
 * @author junlong
 * @version : ConsoleFinderTest.java, v 0.1 2020年08月12日 9:43 PM junlong Exp $
 */
public class ConsoleFinderTest {
  @Test
  public void find() {
    ConsoleFinder finder = ConsoleFinder.getInstance();
    finder.find(new String[] {});
  }
}
