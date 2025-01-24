package camelinaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import org.apache.camel.builder.RouteBuilder;
import stockstats.*;

/**
 * A set of routes that watches a directory for new orders, reads them, converts the order
 * file into a JMS Message and then sends it to the JMS incomingOrders queue hosted
 * on an embedded ActiveMQ broker instance.
 */


public class OrderRouter {
	public static void main(String[] args) throws Exception {
		Map<String, StockStats> stockStatsMap = new ConcurrentHashMap<>();
		ConsumerProcessor MSFTProcessor = new ConsumerProcessor(stockStatsMap);
		ConsumerProcessor IBMProcessor = new ConsumerProcessor(stockStatsMap);
		ConsumerProcessor ORCLProcessor = new ConsumerProcessor(stockStatsMap);
		
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory cf =
	            new ActiveMQConnectionFactory("tcp://localhost:61616");

	    context.addComponent("jms",
	            JmsComponent.jmsComponentAutoAcknowledge(cf));
	    
	    
	    context.addRoutes(new RouteBuilder() {
	    	@Override
		    public void configure() {
	    		// 1. Pub-Sub Topic
	    		// 2. P2P message consumer
	    		// 4. content-based router
	    		// 5. Invalid Msg Channel 
		        from("jms:queue:MPCS_51050_Trade_Engine")
		        	.log("RECEIVED: jms queue: ${body} from file: ${header.CamelFileNameOnly}")
			        .choice()
				        .when(body().regex(".*MSFT.*"))
				        	.process(MSFTProcessor)
				            .to("jms:topic:MPCS_51050_TRADE_TOPIC_MSFT")	
				        .when(body().regex(".*ORCL.*"))
				        	.process(ORCLProcessor)
				            .to("jms:topic:MPCS_51050_TRADE_TOPIC_ORCL")
				        .when(body().regex(".*IBM.*"))
				        	.process(IBMProcessor)
				            .to("jms:topic:MPCS_51050_TRADE_TOPIC_IBM")
				        .otherwise()	
				            .to("jms:topic:UNHANDLED_MESSAGES") // Handle other messages in this topic
				    .end();
	    	}
	    });
    	context.start();
        Thread.sleep(20000); // Adjust the time based on your needs
        context.stop();
       
	}
}
	    	
	    

