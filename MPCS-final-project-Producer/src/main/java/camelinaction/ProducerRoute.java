package camelinaction;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.dataformat.csv.CsvDataFormat;


public class ProducerRoute {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        
        ActiveMQConnectionFactory cf =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        cf.setUserName("admin");
        cf.setPassword("admin");
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(cf));
        
        // 2. P2P message producer
        context.addRoutes(new RouteBuilder() {
        	@Override
            public void configure() {
        		CsvDataFormat csv = new CsvDataFormat();
                csv.setDelimiter(",");
        		from("file:data/999trades?noop=true")
                    .log("RETRIEVED: ${file:name}")
                    .unmarshal().csv()
                    .split(body())
                    .to("jms:queue:MPCS_51050_Trade_Engine");	
            }
        });
        context.start();
        Thread.sleep(20000); // Adjust the time based on your needs
        context.stop();
    }

    
}

