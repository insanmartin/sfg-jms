package guru.springframework.sfgjms.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//It is a good practice to implement Serializabe with 
//the objects use to send/receive message
//In this example we are not going to send the object but text messages
public class HelloWorldMessage implements Serializable
{
	private static final long serialVersionUID = 7084850087518835344L;
	
	private UUID id;
	private String message;
}
