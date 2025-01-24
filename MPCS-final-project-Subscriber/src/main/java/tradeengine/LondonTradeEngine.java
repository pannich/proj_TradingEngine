package tradeengine;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import stockstrategy.StockStrategy;

public class LondonTradeEngine extends TradeEngine {

    public LondonTradeEngine(String name, CamelContext context, StockStrategy stockstrategy) {
    	super(name, context, stockstrategy);
    }

    @Override
	public void setupRoutes() {
	    
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("jms:topic:MPCS_51050_TRADE_TOPIC_IBM")
                    	.log("LDN RECEIVED: jms IBM queue: ${body} from file: ${header.CamelFileNameOnly}")
                    	.process(new SubscriberProcessor("IBM", LondonTradeEngine.this))
                    	.to("file:data/outbox/LDN/IBM?noop=true&fileName=Thread-${threadName}-${header.CamelFileNameOnly}-${date:now:yyyyMMddHHmmssSSS}.out");
                        }
                }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
