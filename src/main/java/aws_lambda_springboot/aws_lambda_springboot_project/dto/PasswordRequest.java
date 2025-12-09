package aws_lambda_springboot.aws_lambda_springboot_project.dto;

public record PasswordRequest(Integer length, Boolean includeSymbols) {
}