package aws_lambda_springboot.aws_lambda_springboot_project.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RoastsService {

	private final static List<String> roasts = Arrays.asList(
			"I've seen sticky notes with better security than your last password.",
			"Finally, a password that isn't your dog's name followed by '123'.",
			"This password is strong. Unlike your will to exercise.",
			"Hackers hate this one simple trick: actually using a complex password.",
			"Your IT department just sighed in relief.",
			"Good job. Now don't save this in a file called 'passwords.txt'.");

	// generate a random roast
	public String getRandomRoast() {
		return roasts.get(new Random().nextInt(roasts.size()));
	}

}
