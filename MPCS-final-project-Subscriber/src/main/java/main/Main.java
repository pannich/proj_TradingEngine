//Run TradeEngine Application in this order
// 1. Run ProducerRoute.java
// 2. Main.java
// 3. OrderRouter.java

package main;

import tradeengine.*;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import report.ReportEngine;
import stockstrategy.*;

public class Main {
    public static void main(String[] args) { 
    	// (1. Pubsub subscriber)
        CamelContext context = new DefaultCamelContext();
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
	    context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(cf));
	    
	    StockStrategy simpleStrategy = new SimpleStats();
	    StockStrategy premiumStrategy = new PremiumStats();
	    
	    // (1. Strategy Design pattern - StockStrategy)
	    // (2. Template Design pattern - TradeEngine)
	    TradeEngine tokyoEngine = new TokyoTradeEngine("TOK", context, simpleStrategy); //IBM MSFT
	    TradeEngine ldnEngine = new LondonTradeEngine("LDN", context, premiumStrategy); // IBM
	    TradeEngine nycEngine = new NewYorkTradeEngine("NYC", context, premiumStrategy); // MSFT ORCL
	    
	    
	    try {
	    	ldnEngine.setupRoutes();
	    	tokyoEngine.setupRoutes();
	    	nycEngine.setupRoutes();
	  
	    	context.start();
	    	Thread.sleep(100000); // Adjust the time based on how long you want the Engine to run.
	    	context.stop();
	    } catch (Exception e) {
            e.printStackTrace();
        }
          
	    // (3. Singleton Design pattern - Report Engine)
	    // (4. Iterator Design pattern - in printReport())
        ReportEngine report = ReportEngine.getInstance();
        report.printReport(tokyoEngine);
        report.printReport(ldnEngine);
        report.printReport(nycEngine);
    }
}
