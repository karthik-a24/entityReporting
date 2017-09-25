package com.test.entityReporting;

import java.time.LocalDateTime;

import com.test.entityReporting.util.ErConst;
import com.test.entityReporting.util.ErUtil;

/**
 * Entity class
 *
 * @author Karthik
 * @creation 24 Sep 2017
 *
 */
public class Entity implements ErConst
{  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Custom constructor with all the attributes
   * @param entityName
   * @param transFlag
   * @param agreedFx
   * @param currency
   * @param instructionDate
   * @param settlementDate
   * @param units
   * @param pricePerUnit
   */
  public Entity(String entityName, boolean transFlag, double agreedFx, String currency, 
                LocalDateTime instructionDate, LocalDateTime settlementDate, int units, double pricePerUnit)
  {
    // Set the private attributes
    this.setEntityName(entityName);
    this.setTransactionFlag(transFlag);
    this.setAgreedFx(agreedFx);
    this.setCurrency(currency);
    this.setInstructionDate(instructionDate);
    this.setSettlementDate(settlementDate);
    this.setUnits(units);
    this.setPricePerUnit(pricePerUnit);
  }
  
////////////////////////////////////////////////////////////////
// Calculations
////////////////////////////////////////////////////////////////
  
  /**
   * Calculate the incoming or outgoing
   * based on the transaction flag.
   */
  public double doCalculate()
  {
    // Calculate the Incoming or Outgoing in USD
    double value = ErUtil.calculateTradeAmount(getPricePerUnit(), getUnits(), getAgreedFx());
    
    // Set values based on the transaction flag in USD.
    if(isTransactionFlag()) // Buying flag hence set Outgoing value
      setOutgoingValue(value);
    else  // Selling flag hence set Incoming value
      setIncomingValue(value);
    return value;
  }
  
  
  /**
   * For debugging purposes.
   * Format the string as required.
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Entity Name: " + entityName + "\n");
    if(isTransactionFlag())
      sb.append("Transaction Type: Buying" + "\n");
    else
      sb.append("Transaction Type: Selling" + "\n");
    sb.append("Agreed Foreign Excahnge: " + agreedFx + "\n");
    sb.append("Currency: " + currency + "\n");
    sb.append("Instruction Date: " + instructionDate + "\n");
    sb.append("Settlement Date: " + settlementDate + "\n");
    sb.append("Number of Units: " + units + "\n");
    sb.append("Price Per Unit: " + pricePerUnit + "\n");
      
    // Return the toString() of the String Buffer
    return sb.toString();
  }
  

////////////////////////////////////////////////////////////////
//Getters/Setters
////////////////////////////////////////////////////////////////

  /**
   * Get the Entity Name
   * @return
   */
  public String getEntityName() { return entityName; }

  /**
   * Set the Entity Name
   * @param entityName
   */
  private void setEntityName(String entityName) { this.entityName = entityName; }

  /**
   * Get Transaction Flag
   * @return 
   * TRUE = BUYING;
   * FALSE = SELLING
   */
  public boolean isTransactionFlag() { return transactionFlag; }

  /**
   * Set the Transaction Flag
   * @param transactionFlag
   */
  private void setTransactionFlag(boolean transactionFlag) { this.transactionFlag = transactionFlag; }

  /**
   * Get the Agreed Foreign Exchange to USD
   * @return 
   */
  public double getAgreedFx() { return agreedFx; }

  /**
   * Set the agreed Foreign Exchange to USD
   * @param agreedFx
   */
  private void setAgreedFx(double agreedFx) { this.agreedFx = agreedFx; }

  /**
   * Get the Currency
   * @return
   */
  public String getCurrency() { return currency; }

  /**
   * Set the Currency
   * @param currency
   */
  private void setCurrency(String currency) { this.currency = currency; }

  /**
   * Get the Instruction Date of the Entity
   * @return
   */
  public LocalDateTime getInstructionDate() { return instructionDate; }

  /**
   * Set the Instruction Date of the Entity
   * @param instructionDate
   */
  private void setInstructionDate(LocalDateTime instructionDate) { this.instructionDate = instructionDate; }

  /**
   * Get the Settlement Date of the Entity
   * @return
   */
  public LocalDateTime getSettlementDate() { return settlementDate; }

  /**
   * Set the Settlement Date of the Entity
   * @param settlementDate
   */
  private void setSettlementDate(LocalDateTime settlementDate) { this.settlementDate = settlementDate; }

  /**
   * Get the Number of Units
   * @return
   */
  public int getUnits() { return units; }

  /**
   * Set the Number of Units
   * @param units
   */
  private void setUnits(int units) { this.units = units; }

  /**
   * Get the Per Unit Price
   * @return
   */
  public double getPricePerUnit() { return pricePerUnit; }

  /**
   * Set the Per Unit Price
   * @param pricePerUnit
   */
  private void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }

  /**
   * Get the incoming value
   * @return
   */
  public double getIncomingValue() { return incomingValue; }

  /**
   * Set the incoming value
   * @param incomingValue
   */
  private void setIncomingValue(double incomingValue) { this.incomingValue = incomingValue; }

  /**
   * Get the outgoing value
   * @return
   */
  public double getOutgoingValue() { return outgoingValue; }

  /**
   * Set the outgoing value
   * @param outgoingValue
   */
  private void setOutgoingValue(double outgoingValue) { this.outgoingValue = outgoingValue; }

////////////////////////////////////////////////////////////////
//Attributes/Properties
////////////////////////////////////////////////////////////////

  private String entityName = "Entity";  
  private boolean transactionFlag = true;
  private double agreedFx = 1;
  private String currency = USD;
  private LocalDateTime instructionDate = LocalDateTime.MIN;
  private LocalDateTime settlementDate = LocalDateTime.MIN;
  private int units = 0;
  private double pricePerUnit = 0;
  private double incomingValue = 0;
  private double outgoingValue = 0;
}
