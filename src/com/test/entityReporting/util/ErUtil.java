package com.test.entityReporting.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Utility class.
 *
 * @author Karthik
 * @creation 24 Sep 2017
 *
 */
public final class ErUtil implements ErConst
{
  /**
   * Check if the currency'sworking week
   * is Monday to Friday
   * @param currency
   * @return 
   * TRUE = Currency is Mon to Fri;
   * FALSE = Currency is Sun to Thur
   */
  public static boolean isCurrencyMonToFri(String currency)
  {
    // Return FALSE if AED or SAR to indicate Sun to Thur week
    if(currency.equals(AED) || currency.equals(SAR))
      return false;
    // Return TRUE by default for all other currencies
    return true;
  }
  
  /**
   * Get the appropriate settlement 
   * date based on the working week.
   * @param isWeekMonToFri
   * @param instructionDate
   * @return Settlement Date
   */
  public static LocalDateTime getSettlementDate(boolean isWeekMonToFri, LocalDateTime settlementDate)
  {
    // Process for Monday to Friday working week
    if(isWeekMonToFri)
    {
      // Add two days and return if instruction date is a Saturday
      if(settlementDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
        return settlementDate.plusDays(2);
      // Add one day and return if instruction date is a Sunday
      else if(settlementDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
        return settlementDate.plusDays(1);
    }    
    // Process for Sunday to Thursday working week
    else
    {
      // Add two days and return if instruction date is a Friday
      if(settlementDate.getDayOfWeek().equals(DayOfWeek.FRIDAY))
        return settlementDate.plusDays(2);
      // Add one day and return if instruction date is a Saturday
      else if(settlementDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
        return settlementDate.plusDays(1);
    }
    // Return since instruction date is within a working week
    return settlementDate;
  }
  
  /**
   * Calculate Amount of Trade
   * @param pricePerUnit
   * @param units
   * @param agreedFx
   * @return
   */
  public static double calculateTradeAmount(double pricePerUnit, int units, double agreedFx)
  {
    return pricePerUnit * units * agreedFx;
  }
}