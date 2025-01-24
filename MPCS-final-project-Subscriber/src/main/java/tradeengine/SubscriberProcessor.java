package tradeengine;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import report.ReportEngine;
import stockstats.StockStats;
import stockstats.StockStatsMap;


public class SubscriberProcessor implements Processor {
	private String stockName;
	private TradeEngine engine;
	private StockStatsMap localStats;
	
	public SubscriberProcessor(String name, TradeEngine engine) {
		this.stockName = name;
		this.engine = engine;
		this.localStats = engine.getLocalStats();
	}

    @Override
    public void process(Exchange exchange) throws Exception {
    	
    	String json = exchange.getIn().getBody(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        
        // update stats
        StockStats stockStats = engine.updateStockStats(rootNode);
        localStats.updateStats(stockStats);
        
        // generate body string
        ReportEngine report = ReportEngine.getInstance();
        String stockSummary = report.generateStockStatsSummary(stockStats);
        
        // Set header
        exchange.getIn().setHeader("stockName", stockName);
        
		//Setting the formatted string as the body of the exchange
		exchange.getIn().setBody(stockSummary);

    }
}
