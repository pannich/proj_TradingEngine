package tradeengine;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import stockstrategy.StockStrategy;

public class NewYorkTradeEngine extends TradeEngine {

    public NewYorkTradeEngine(String name, CamelContext context, StockStrategy stockstrategy) {
        super(name, context, stockstrategy);
    }

    @Override
	public void setupRoutes() {    
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("jms:topic:MPCS_51050_TRADE_TOPIC_ORCL")
                    	.log("NYC RECEIVED: jms ORCL queue: ${body} from file: ${header.CamelFileNameOnly}")
                        .process(new SubscriberProcessor("ORCL", NewYorkTradeEngine.this))
                        .to("file:data/outbox/NYC/ORCL?noop=true&fileName=Thread-${threadName}-${header.CamelFileNameOnly}.out");
                    
                    from("jms:topic:MPCS_51050_TRADE_TOPIC_MSFT")
	                	.log("NYC RECEIVED: jms ORCL queue: ${body} from file: ${header.CamelFileNameOnly}")
	                    .process(new SubscriberProcessor("MSFT", NewYorkTradeEngine.this))
	                    .to("file:data/outbox/NYC/MSFT?noop=true&fileName=Thread-${threadName}-${header.CamelFileNameOnly}.out");
                }
            });
            context.start();
            Thread.sleep(15000); // Adjust the time based on your needs
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
