package aws_lambda_springboot.aws_lambda_springboot_project.controller;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aws_lambda_springboot.aws_lambda_springboot_project.dto.PasswordRequest;
import aws_lambda_springboot.aws_lambda_springboot_project.dto.PasswordResponse;
import aws_lambda_springboot.aws_lambda_springboot_project.service.RoastsService;

@RestController
@RequestMapping("/api/v1")
public class PasswordController {

	private final RoastsService roastService;

	PasswordController(RoastsService roastservice) {
		this.roastService = roastservice;
	}

	@PostMapping("/generate")
	public ResponseEntity<?> generate(@RequestBody(required = false) PasswordRequest request) {
		try {
			int finalLength = (request.length() == null || (request.length() < 8 || request.length() > 40)) ? 16
					: request.length();
			
			boolean useSymbols = request.includeSymbols() == null || request.includeSymbols();

			String password = createPassword(finalLength, useSymbols);
			String roast = roastService.getRandomRoast();

			return ResponseEntity.ok(new PasswordResponse(password, roast));
		} catch (Exception e) {
			return new ResponseEntity<PasswordResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String createPassword(int length, boolean useSymbols) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		if (useSymbols)
			chars += "!@#$%^&*()-_=+";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
			sb.append(chars.charAt(random.nextInt(chars.length())));
		return sb.toString();
	}
}
