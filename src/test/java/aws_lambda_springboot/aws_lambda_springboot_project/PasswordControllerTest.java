package aws_lambda_springboot.aws_lambda_springboot_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import aws_lambda_springboot.aws_lambda_springboot_project.controller.PasswordController;
import aws_lambda_springboot.aws_lambda_springboot_project.dto.PasswordRequest;
import aws_lambda_springboot.aws_lambda_springboot_project.dto.PasswordResponse;
import aws_lambda_springboot.aws_lambda_springboot_project.service.RoastsService;

@ExtendWith(MockitoExtension.class)
class PasswordControllerTest {

	@Mock
	private RoastsService roastService;

	@InjectMocks
	private PasswordController controller;

	@Test
	void shouldGeneratePasswordAndIncludeRoast() {
		PasswordRequest request = new PasswordRequest(20, true);
		when(roastService.getRandomRoast()).thenReturn("Mocked Roast Message");
		ResponseEntity<?> response = controller.generate(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(20, ((PasswordResponse) response.getBody()).password().length());
		assertEquals("Mocked Roast Message", ((PasswordResponse) response.getBody()).roast());
		verify(roastService).getRandomRoast();
	}

	@Test
	void shouldApplyDefaultsWhenRequestHasNulls() {
		PasswordRequest request = new PasswordRequest(null, null);
		when(roastService.getRandomRoast()).thenReturn("Default Roast");
		ResponseEntity<?> response = controller.generate(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(16, ((PasswordResponse) response.getBody()).password().length());
		assertNotNull(((PasswordResponse) response.getBody()).password());
	}

	@Test
	void shouldNotIncludeSymbolsWhenUseSymbolsIsFalse() {
		PasswordRequest request = new PasswordRequest(20, false);
		ResponseEntity<?> response = controller.generate(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(((PasswordResponse) response.getBody()).password().matches("[a-zA-Z0-9]+"),
				"Password should be alphanumeric only.");
	}

	@Test
	void shouldReturnSixteenLenPasswordForNegativeLength() {
		PasswordRequest request = new PasswordRequest(-5, false);
		ResponseEntity<?> response = controller.generate(request);
		assertEquals(16, ((PasswordResponse) response.getBody()).password().length());
	}
}