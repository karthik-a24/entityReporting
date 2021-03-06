package com.test.entityReporting.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Entity Test Runner
 *
 * @author Karthik
 * @creation 24 Sep 2017
 *
 */
public class EntityTestRunner
{
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    Result result = JUnitCore.runClasses(EntityTest.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println("Tests ran succesfully: " + result.wasSuccessful());
  }

}
