package com.test.entityReporting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.entityReporting.util.ErConst;
import com.test.entityReporting.util.ErUtil;

/**
 * Entity Reporter
 *
 * @author Karthik
 * @creation 24 Sep 2017
 *
 */
public class EntityReporter implements ErConst
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Default empty Constructor
   */
  public EntityReporter()
  {
  }
  
////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////
  
  /**
   * 
   * @param entityName
   * @param transFlag
   * @param agreedFx
   * @param currency
   * @param instructionDate
   * @param settlementDate
   * @param units
   * @param pricePerUnit
   * @throws Exception
   */
  public void addEntity(String entityName, boolean transFlag, double agreedFx, String currency, 
                 LocalDateTime instructionDate, LocalDateTime settlementDate, int units, double pricePerUnit) throws Exception
  {
    /*
     *  Perform some basic sanity checks 
     *  for the integrity of input data.
     */
    if(agreedFx <= 0)
      throw new Exception("Agreed Foreign Exchange cannot be ZERO or NEGATIVE.");
    if(units <= 0)
      throw new Exception("Units to be traded cannot be ZERO or NEGATIVE.");
    if(pricePerUnit <= 0)
      throw new Exception("Price per unit cannot be ZERO or NEGATIVE.");
    if(instructionDate.isAfter(settlementDate))
      throw new Exception("Instruction date cannot be after settlement date.");      

    // Update the Settlement Date based on the currency
    settlementDate = ErUtil.getSettlementDate(ErUtil.isCurrencyMonToFri(currency), settlementDate);
    // Create the entity
    Entity entity = new Entity(entityName, transFlag, agreedFx, currency, 
                               instructionDate, settlementDate, units, pricePerUnit);
    // Calculate the entity trade
    entity.doCalculate();
    // Add the entity into the hashmap
    entities.put(entity.getEntityName(), entity);
  }
  
  /**
   * Generate the reporting lists
   */
  public void generateReport()
  {
    // Return if Entities is null or empty
    if (entities == null || entities.isEmpty())
      return;
    
    // Create the entity sorting list by Settlement Date (Latest First)
    List<Entity> entitiesByDate = new ArrayList<Entity>(entities.values());
    // Sort the entities list by Settlement Date - Latest first
    Collections.sort(entitiesByDate, new Comparator<Entity>() {      
      /**
       * Settlement Date comparator.
       * Latest date first.
       */
      public int compare(Entity o1, Entity o2) {
        if(o1.getSettlementDate().isAfter(o2.getSettlementDate()))  // Value 1 grater than Value 2
          return 1; // Return 1 to swap
        else if(o1.getSettlementDate().isBefore(o2.getSettlementDate())) // // Value 2 grater than Value 1
          return -1; 
        else  // Value 1 equal to Value 2
          return 0;
      }
    });
    // Initialise the daily incoming and outgoing lists
    dailySettledIncoming = new ArrayList<EntityReporter.DailySettledAmount>();
    dailySettledOutgoing = new ArrayList<EntityReporter.DailySettledAmount>();

    LocalDateTime dailyDate = null;
    double dailyIncSettledValue = 0;
    double dailyOutSettledValue = 0;
    // Calculate settled daily outgoing and incoming amounts.
    // And update the respective list for reporting.
    for(Entity entity : entitiesByDate)
    {
      if(dailyDate == null) // Process the first entity
      {
        // Set the Daily totals and date
        dailyDate = entity.getSettlementDate();
        dailyIncSettledValue = entity.getIncomingValue();
        dailyOutSettledValue = entity.getOutgoingValue();
      }
      else if(dailyDate.equals(entity.getSettlementDate()))  // Add the values for same date entities
      {
        // Update the daily totals
        dailyIncSettledValue += entity.getIncomingValue();
        dailyOutSettledValue += entity.getOutgoingValue();          
      }
      else if(!dailyDate.equals(entity.getSettlementDate()))  // Process the next date
      {
        // Add the Incoming and Outgoing Daily settled amounts to the List
        DailySettledAmount dsaInc = new DailySettledAmount(dailyDate, dailyIncSettledValue);
        dailySettledIncoming.add(dsaInc); // Add to the Incoming list
        DailySettledAmount dsaOut = new DailySettledAmount(dailyDate, dailyOutSettledValue);
        dailySettledOutgoing.add(dsaOut); // Add to the Outgoing list
        // Reset the daily totals and date
        dailyDate = entity.getSettlementDate();
        dailyIncSettledValue = entity.getIncomingValue();
        dailyOutSettledValue = entity.getOutgoingValue();
      }
    }
    // Add the final Incoming and Outgoing Daily settled amounts to the List
    DailySettledAmount dsaInc = new DailySettledAmount(dailyDate, dailyIncSettledValue);
    dailySettledIncoming.add(dsaInc); // Add to the Incoming list
    DailySettledAmount dsaOut = new DailySettledAmount(dailyDate, dailyOutSettledValue);
    dailySettledOutgoing.add(dsaOut); // Add to the Outgoing list
    
    
    // Create the outgoing sorting list
    entitiesByOutgoing = new ArrayList<Entity>(entities.values());
    // Sort the outgoing list
    Collections.sort(entitiesByOutgoing, new Comparator<Entity>() {      
      /**
       * Outgoing Value comparator
       */
      public int compare(Entity o1, Entity o2) {
        double diff = o1.getOutgoingValue() - o2.getOutgoingValue();
        if(diff > 0)  // Value 1 grater than Value 2
          return -1;
        else if(diff < 0) // Value 2 grater than Value 1
          return 1;  // Swap if lesser
        else  
          return 0;
      }
    });
    
    // Create the incoming sorting list
    entitiesByIncoming = new ArrayList<Entity>(entities.values());
    // Sort the outgoing list
    Collections.sort(entitiesByIncoming, new Comparator<Entity>() {      
      /**
       * Incoming Value comparator
       */
      public int compare(Entity o1, Entity o2) {
        double diff = o1.getIncomingValue() - o2.getIncomingValue();
        if(diff > 0)  // Value 1 grater than Value 2
          return -1;
        else if(diff < 0) // Value 2 grater than Value 1
          return 1;  // Swap if lesser
        else  // Value 1 equal to Value 2
          return 0;
      }
    });
    
  }
  
  /**
   * Print the report
   */
  public void printReport()
  {    
    /*
     * Print the reports on the console
     */

    System.out.println("***** Amount in USD, Daily Settled Incoming ******");
    for (DailySettledAmount dsa : dailySettledIncoming) {
      System.out.println(dsa.date.toLocalDate() + ": \t USD " + dsa.dailySettledAmount);
    }
    System.out.println("--------------------------------------------------");
    System.out.println();
    
    System.out.println("***** Amount in USD, Daily Settled Outgoing ******");
    for (DailySettledAmount dsa : dailySettledOutgoing) {
      System.out.println(dsa.date.toLocalDate() + ": \t USD " + dsa.dailySettledAmount);
    }
    System.out.println("--------------------------------------------------");
    System.out.println();
    
    int rank = 1; // Reset the rank for Incoming Values
    System.out.println("******* Entities Ranked by Incoming in USD *******");
    for (Entity entity : entitiesByIncoming) {
      System.out.println(rank++ +"." + entity.getEntityName() + ": \t USD " + entity.getIncomingValue());
    }
    System.out.println("--------------------------------------------------");
    System.out.println();    
    
    rank = 1; // Rank for Outgoing values
    System.out.println("******* Entities Ranked by Outgoing in USD *******");
    for (Entity entity : entitiesByOutgoing) {
      System.out.println(rank++ +"." + entity.getEntityName() + ": \t USD " + entity.getOutgoingValue());
    }
    System.out.println("--------------------------------------------------");
    System.out.println();
  }
  
////////////////////////////////////////////////////////////////
// Daily Settled Amount class
////////////////////////////////////////////////////////////////

  // Class to hold the daily settled amount.
  // Used for both incoming and outgoing.
  class DailySettledAmount{
    DailySettledAmount(LocalDateTime date, double dailySettledAmount)
    {
      this.date = date;
      this.dailySettledAmount = dailySettledAmount;
    }
    
    private LocalDateTime date = LocalDateTime.MIN;
    private double dailySettledAmount = 0;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private Map<String, Entity> entities = new HashMap<String, Entity>(); 
  List<Entity> entitiesByIncoming = new ArrayList<Entity>();
  List<Entity> entitiesByOutgoing = new ArrayList<Entity>();
  List<DailySettledAmount> dailySettledIncoming = new ArrayList<EntityReporter.DailySettledAmount>();
  List<DailySettledAmount> dailySettledOutgoing = new ArrayList<EntityReporter.DailySettledAmount>();
}
