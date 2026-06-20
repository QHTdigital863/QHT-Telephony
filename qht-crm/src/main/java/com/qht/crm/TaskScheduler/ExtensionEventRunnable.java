package com.qht.crm.TaskScheduler;

import com.qht.crm.entity.dto.BotInputDTO;
import com.qht.crm.ws.client.MyStompSessionHandler;

import lombok.Data;

@Data
public class ExtensionEventRunnable implements Runnable{

//  private String organization;
//  private ApplicationContext applicationContext;
  
  BotInputDTO msg ;
    
  @Override
  public void run() {
      System.out.println("ExtensionEventRunnable");
      try {
          MyStompSessionHandler.sendMessage("/qht/sendcalldetails", msg);
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
  }
}