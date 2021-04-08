package guru.springframework.sfgjms.listener;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloMessageListener
{
	//this is a preconfigured JMS template to talk to our ActiveMQ instance
	private final JmsTemplate jmsTemplate;
	

	//Configure a listener (JmsListener annotation) for the MY_QUEUE queue
	//Payload annotation tells Spring Framework to deserialize up the payload of the JMS message 
	//so it is saying, I'm expecting a HelloWorldMessage from the queue
	//Headers annotation tells SF to get the headers of the jms message
	@JmsListener( destination = JmsConfig.MY_QUEUE )
	public void listen( @Payload HelloWorldMessage helloWorldMessage,
						@Headers MessageHeaders headers, 
						Message message )
	{
		;//System.out.println( "I Got a Message!!!" );
		//System.out.println( helloWorldMessage );
	}
	
	@JmsListener( destination = JmsConfig.MY_SEND_RCV_QUEUE )
	public void listenForHello( @Payload HelloWorldMessage helloWorldMessage,
						@Headers MessageHeaders headers,
						///jms implementation
						Message jmsMsg
						//spring implementation
						//org.springframework.messaging.Message springMsg
						)  throws JMSException
	{
		HelloWorldMessage returnMessage = HelloWorldMessage
				.builder()
				.id( UUID.randomUUID() )
				.message( "World!" )
				.build();
		
		//reply to the message (with Spring message)
		//we have to get the reply queue from the headers
		//jmsTemplate.convertAndSend( (javax.jms.Destination) springMsg.getHeaders().get( "jms_replyTo" ), returnMessage );
		
		//repy to the message (the first argument is the destination)
		jmsTemplate.convertAndSend( jmsMsg.getJMSReplyTo(), returnMessage );
	}
}
