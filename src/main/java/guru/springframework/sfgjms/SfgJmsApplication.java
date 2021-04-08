package guru.springframework.sfgjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication
{
	public static void main(String[] args) throws Exception
	{
/*
 		//before running the spring boot context, we set up our messaging embeded server		
		ActiveMQServer server = ActiveMQServers.newActiveMQServer( new ConfigurationImpl()
									//non-persistance
									.setPersistenceEnabled( false )
									.setJournalDirectory( "target/data/journal" )
									//security disabled
									.setSecurityEnabled( false )
									//the way to connect: enabled communitation with the vm
									.addAcceptorConfiguration( "invm", "vm://0" ) 
								);
		server.start();
*/		
		
		SpringApplication.run(SfgJmsApplication.class, args);
	}
}
