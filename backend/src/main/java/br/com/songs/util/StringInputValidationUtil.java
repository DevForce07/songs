package br.com.songs.util;

import java.util.regex.Pattern;

public class StringInputValidationUtil {

	private static final Pattern CNPJ_REGEX = Pattern.compile("^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$");
	private static final Pattern CPF_REGEX = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$");
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
	private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
	private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");

	public static boolean isValidCnpj(String cnpj) {
		return cnpj!=null && CNPJ_REGEX.matcher(cnpj).find();
	}

	public static boolean isValidCpf(String cpf) {
		return cpf!=null && CPF_REGEX.matcher(cpf).find();
	}
	
	public static boolean isValidEmail(String email) {
		return email != null &&EMAIL_REGEX.matcher(email).find();
	}
	
	public static boolean isValidPassword(String password) {
		return password != null && PASSWORD_REGEX.matcher(password).find();
	}
	
	public static boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber!=null && PHONE_NUMBER_REGEX.matcher(phoneNumber).find();
	}
}
