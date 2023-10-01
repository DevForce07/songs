package br.com.songs.converter.users;

import br.com.songs.domain.entity.*;
import br.com.songs.exception.OperationException;
import br.com.songs.exception.UserNotFoundException;
import br.com.songs.util.StringInputValidationUtil;
import br.com.songs.web.dto.acting.ActingAreaDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPutDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPutDTO;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PerfilConverter {
    private static final String MESSAGE_PASSWORD_INCORRECT = "password must contain at least eight characters, at least one letter, one number and one special character";

    public static void checkFieldsFromUser(Perfil user, boolean verifyPassword) {
        if (!StringInputValidationUtil.isValidCpf(user.getDocument())) {
            throw new OperationException("cpf not valid");
        }

        if (!StringInputValidationUtil.isValidEmail(user.getEmail())) {
            throw new OperationException("email not valid");
        }

        if (StringUtils.isBlank(user.getName())) {
            throw new OperationException("name can not to be blank");
        }

//        if (!StringInputValidationUtil.isValidPhoneNumber(user.getPhoneNumber())) {
//            throw new OperationException("phone number is wrong");
//        }

        if (verifyPassword && StringUtils.isBlank(user.getPassword())) {
            throw new OperationException("password can not to be blank");
        }

        if (verifyPassword && !StringInputValidationUtil.isValidPassword(user.getPassword())) {
            throw new OperationException(MESSAGE_PASSWORD_INCORRECT);
        }
    }

    public static AdminOngRequestGetDTO adminOngEntityToConvertUserOngRequestGetDTO(AdminOngPerfil user){
        return AdminOngRequestGetDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).cpf(user.getDocument()).build();
    }
    public static AdminOngPerfil convertUserOngRequestPostDTOToAdminOngEntity(AdminOngRequestPostDTO userDTO){
        return AdminOngPerfil.builder().email(userDTO.getEmail()).name(userDTO.getName()).document(userDTO.getCpf()).password(userDTO.getPassword()).build();
    }

    public static AdminOngPerfil convertUserOngRequestPutDTOToAdminOngEntity(AdminOngRequestPutDTO userDTO){
        return AdminOngPerfil.builder().id(userDTO.getId()).email(userDTO.getEmail()).name(userDTO.getName()).document(userDTO.getCpf()).build();
    }

    public static EmployeeRequestGetDTO employeeEntityToConvertEmployeeRequestGetDTO(EmployeePerfil user){
        return EmployeeRequestGetDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).cpf(user.getDocument()).birthDate(user.getBirthDate()).ongEmployeeId(user.getOngEmployeeId()).sex(user.getSex()) .build();
    }
    public static EmployeePerfil convertEmployeeRequestPostDTOToEmployeeEntity(EmployeeRequestPostDTO userDTO){
        return EmployeePerfil.builder().email(userDTO.getEmail()).name(userDTO.getName()).document(userDTO.getCpf()).password(userDTO.getPassword()).birthDate(userDTO.getBirthDate()).ongEmployeeId(userDTO.getOngEmployeeId()).sex(userDTO.getSex()) .build();
    }

    public static EmployeePerfil convertEmployeeRequestPutDTOToEmployeeEntity(EmployeeRequestPutDTO userDTO){
        return EmployeePerfil.builder().id(userDTO.getId()).email(userDTO.getEmail()).name(userDTO.getName()).document(userDTO.getCpf()).password(userDTO.getPassword()).birthDate(userDTO.getBirthDate()).ongEmployeeId(userDTO.getOngEmployeeId()).sex(userDTO.getSex()).build();
    }

    public static Perfil extratUserOrThrowException(Optional<Perfil> userCurrent) {
        return userCurrent.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public static PerfilOngRequestGetDTO convertPerfilEntityToPerfilRequestGetDTO(Perfil user) {
        List<OngRequestGetDTO> ongRequestGetDTOS = user.getOngs().stream().map(PerfilConverter::convertUserOngEntityToUserOngRequestGetDTO).collect(Collectors.toList());
        return PerfilOngRequestGetDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).cpf(user.getDocument()).ongs(ongRequestGetDTOS).build();
    }

    public static OngRequestGetDTO convertUserOngEntityToUserOngRequestGetDTO(Ong user) {
        return OngRequestGetDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName())
                .urlImage(user.getUrlImage()).cnpj(user.getCnpj()).address(user.getAddress())
                .phoneNumber(user.getPhoneNumber()).actingArea(convertActingAreaEntiryToActingAreaDTO(user.getActingArea())).build();
    }

    public static ActingArea convertActingAreaDTOToActingAreaEntiry(ActingAreaDTO actingAreaDTO ) {
        return ActingArea.builder().id(actingAreaDTO.getId()).name(actingAreaDTO.getName()).build();
    }

    public static ActingAreaDTO convertActingAreaEntiryToActingAreaDTO(ActingArea actingArea) {
        return ActingAreaDTO.builder().id(actingArea.getId()).name(actingArea.getName()).build();
    }
}
