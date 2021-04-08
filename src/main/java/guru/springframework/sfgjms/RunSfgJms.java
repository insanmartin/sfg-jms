package guru.springframework.sfgjms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.sender.HelloSender;

//@RequiredArgsConstructor
@Component
public class RunSfgJms implements CommandLineRunner
{
	@Autowired
	private HelloSender sender;

	@Override
	public void run(String... args) throws Exception
	{
		sender.sendAndReceiveMessage();
	}
}
