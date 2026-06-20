package com.qht.crm.TaskScheduler;

import com.qht.crm.entity.dto.BotInputDTO;
import com.qht.crm.ws.client.MyStompSessionHandler;

import lombok.Data;

@Data
public class GenericEventRunnable implements Runnable{

//    private String organization;
//    private ApplicationContext applicationContext;
    
	BotInputDTO msg;
    
    @Override
    public void run() {
        System.out.println("GenericEventRunnable");
        try {
        	  MyStompSessionHandler.sendMessage("/qht/sendevent", msg);
        }
        catch(Exception e)
        {
      	  e.printStackTrace();
        }
    }
}
