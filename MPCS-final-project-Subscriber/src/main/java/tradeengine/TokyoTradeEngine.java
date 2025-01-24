package tradeengine;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import stockstrategy.StockStrategy;

// Reading from the topics it subscribes and store the stats data in its stockIterator
public class TokyoTradeEngine extends TradeEngine {

    public TokyoTradeEngine(String name, CamelContext context, StockStrategy stockstrategy) {
        super(name, context, stockstrategy);
    }

    @Override
	public void setupRoutes() {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("jms:topic:MPCS_51050_TRADE_TOPIC_IBM")
                    	.log("TOK RECEIVED: jms IBM queue: ${body} from file: ${header.CamelFileNameOnly}")
                        .process(new SubscriberProcessor("IBM", TokyoTradeEngine.this))
                        .to("file:data/outbox/TOKYO/IBM?noop=true&fileName=Thread-${threadName}-${header.CamelFileNameOnly}-${date:now:yyyyMMddHHmmssSSS}.out");
                    
                    from("jms:topic:MPCS_51050_TRADE_TOPIC_MSFT")
                        .process(new SubscriberProcessor("MSFT", TokyoTradeEngine.this))
                        .to("file:data/outbox/TOKYO/MSFT?noop=true&fileName=Thread-${threadName}-${header.CamelFileNameOnly}-${date:now:yyyyMMddHHmmssSSS}.out");
                }
            });      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
