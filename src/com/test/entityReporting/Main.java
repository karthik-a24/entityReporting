package com.test.entityReporting;

import java.time.LocalDateTime;
import java.time.Month;

import com.test.entityReporting.util.ErConst;

public class Main  implements ErConst
{

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
    // Create the Entity Reporter and add the entities
    EntityReporter er = new EntityReporter();
    er.addEntity("GbpEntity", true, 1.35, GBP, LocalDateTime.of(2016, Month.JANUARY, 4, 0, 0),
                   LocalDateTime.of(2016, Month.JANUARY, 5, 0, 0), 10, 100);
    er.addEntity("EurEntity", false, 1.19, EUR, LocalDateTime.of(2016, Month.JANUARY, 2, 0, 0),
                   LocalDateTime.of(2016, Month.JANUARY, 3, 0, 0), 10, 100);
    er.addEntity("UsdEntity", false, 1, USD, LocalDateTime.of(2016, Month.JANUARY, 5, 0, 0),
                   LocalDateTime.of(2016, Month.JANUARY, 6, 0, 0), 10, 100);
    er.addEntity("SarEntity", true, 0.27, SAR, LocalDateTime.of(2016, Month.JANUARY, 7, 0, 0),
                   LocalDateTime.of(2016, Month.JANUARY, 8, 0, 0), 10, 100);
    er.addEntity("SgpEntity", true, 0.50, SGP, LocalDateTime.of(2016, Month.JANUARY, 6, 0, 0),
                   LocalDateTime.of(2016, Month.JANUARY, 7, 0, 0), 10, 100);
    er.addEntity("AedEntity", false, 0.22, AED, LocalDateTime.of(2016, Month.JANUARY, 4, 0, 0),
                   LocalDateTime.of(2016, Month.JANUARY, 6, 0, 0), 10, 100);
    // Generate the report
    er.generateReport();
    // Print the Report
    er.printReport();
  }

}
