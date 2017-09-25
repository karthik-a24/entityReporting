package com.test.entityReporting.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import com.test.entityReporting.Entity;
import com.test.entityReporting.util.ErUtil;

/**
 * 
 *  - Insert description here.
 *
 * @author Karthik
 * @creation 24 Sep 2017
 *
 */
public class EntityTest
{
  Entity testEntity = new Entity("TestEntity", true, 1.35, "GBP", LocalDateTime.of(2017, Month.SEPTEMBER, 22, 0, 0),
                                 LocalDateTime.of(2017, Month.SEPTEMBER, 23, 0, 0), 10, 100);
      
  
  /*
   * Test to check Working week for a currency
   */
  @Test
  public void testCurrency()
  {
    boolean testCurrencyMonToFri = true;
    boolean isCurrencyMonToFri = ErUtil.isCurrencyMonToFri(testEntity.getCurrency());
    assertEquals("Working week for currency test failed", testCurrencyMonToFri, isCurrencyMonToFri);
  }
  
  /*
   * Test to check Working week for a currency
   */
  @Test
  public void testSettlementDate()
  {
    LocalDateTime testSettlementDay = LocalDateTime.of(2017, Month.SEPTEMBER, 25, 0, 0);
    LocalDateTime actualSettlementDay = ErUtil.getSettlementDate(true, testEntity.getSettlementDate());
    assertEquals("Trade settlement date test failed.", testSettlementDay, actualSettlementDay);
  }
  
  
  /*
   * Test to check Trade amount calculation
   */
  @Test
  public void testTradeAmount() {    
    double testTradeAmount = 1350;
    double actualTradeAmount = testEntity.doCalculate();
    assertEquals("Trade Amount test failed", testTradeAmount, actualTradeAmount, 0.0);
  }
}
