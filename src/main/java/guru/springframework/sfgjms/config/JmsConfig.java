package guru.springframework.sfgjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
//Even we are using ActiveMQ-Artemis implementation we can use the jms api class
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

//this is a configuration class -> tells Spring to scan for methods annotated as beans
@Configuration
public class JmsConfig 
{
	public static final String MY_QUEUE = "my-hello-world"; 
	public static final String MY_SEND_RCV_QUEUE = "replybacktome";
	
	@Bean
	public MessageConverter messageConverter()
	{
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		//type of message -> text
		converter.setTargetType( MessageType.TEXT );
		//jms property: specify the name of the JMS message property that carries the type id for the
		//contained object: either a mapped id value or a raw Java class name.
		//This property needs to be set in order to allow for converting from an incoming message to a Java object
		//In this example the value of this property would/should be guru.springframework.sfgjms.model.HelloWorldMessage
		converter.setTypeIdPropertyName( "_type" );
		
		return converter;
	}
}
