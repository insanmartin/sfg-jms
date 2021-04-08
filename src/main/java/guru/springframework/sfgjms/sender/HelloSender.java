package guru.springframework.sfgjms.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloSender
{
	//this is a preconfigured JMS template to talk to our ActiveMQ instance
	private final JmsTemplate jmsTemplate;
	private final ObjectMapper objectMapper;
	
	//we set up the task config at TaskConfig that gets hold of this bean and execute the task
	//(configure to send a message every 2 seconds)
	@Scheduled( fixedRate = 2000 )
	public void sendMessage()
	{
		//System.out.println( "I'm sending a message" );
		HelloWorldMessage message = HelloWorldMessage
									.builder()
									.id( UUID.randomUUID() )
									.message( "Hello World!" )
									.build();
		
		//convertAndSend methods is going to use under the covers the converter configured in JmsConfig
		//we are passing the message and to which queue we want to send it
		jmsTemplate.convertAndSend( JmsConfig.MY_QUEUE, message );
		//System.out.println( "Message Sent!" );
	}
	
	//@Scheduled( fixedRate = 2000 )
	public void sendAndReceiveMessage() throws JMSException
	{
		//System.out.println( "I'm sending a message" );
		HelloWorldMessage message = HelloWorldMessage
									.builder()
									.id( UUID.randomUUID() )
									.message( "Hello" )
									.build();
		
		//this is going to send a message and then return the comeback message
		Message reciveMsg = jmsTemplate.sendAndReceive( JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
			//we have to create the message using MessageCreator - SF provides the objectMapper
			//this does the same what jmsTemplate.convertAndSend does inside
			@Override
			public Message createMessage( Session session ) throws JMSException {
				try
				{
					Message helloMessage = session.createTextMessage( objectMapper.writeValueAsString( message ) );
					//needed by SF to serialize - deserialize the payload of the message
					helloMessage.setStringProperty( "_type", "guru.springframework.sfgjms.model.HelloWorldMessage" );
					
					System.out.println( "Hello send!" );
					return helloMessage;
				}
				catch( JsonProcessingException ex )
				{
					throw new JMSException( "boom" );
				}
			}
		} );
		
		System.out.println( reciveMsg.getBody( String.class ) );
	}
}
