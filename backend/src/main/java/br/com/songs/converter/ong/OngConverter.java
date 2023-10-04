package br.com.songs.converter.ong;

import br.com.songs.domain.entity.ActingArea;
import br.com.songs.domain.entity.Ong;
import br.com.songs.exception.OperationException;
import br.com.songs.util.StringInputValidationUtil;
import br.com.songs.web.dto.acting.ActingAreaDTO;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestPostDTO;
import br.com.songs.web.dto.ong.OngRequestPutDTO;
import org.apache.commons.lang3.StringUtils;

public class OngConverter {

    public static void checkFieldsFromOng(Ong ong) {
        if (!StringInputValidationUtil.isValidCnpj(ong.getCnpj())) {
            throw new OperationException("cnpj not valid");
        }

        if (!StringInputValidationUtil.isValidEmail(ong.getEmail())) {
            throw new OperationException("email not valid");
        }

        if (StringUtils.isBlank(ong.getName())) {
            throw new OperationException("name can not to be blank");
        }

//        if (!StringInputValidationUtil.isValidPhoneNumber(ong.getPhoneNumber())) {
//            throw new OperationException("phone number is wrong");
//        }

        if (ong.getAddress()==null) {
            throw new OperationException("address is empty");
        }

    }

    public static OngRequestGetDTO convertOngEntityToOngRequestGetDTO(Ong ong){
        return OngRequestGetDTO.builder().id(ong.getId()).name(ong.getName())
                .cnpj(ong.getCnpj()).email(ong.getEmail()).address(ong.getAddress()).description(ong.getDescription()).urlImage(ong.getUrlImage()).phoneNumber(ong.getPhoneNumber())
                .actingArea(convertActingAreaEntityToActingAreaDTO(ong.getActingArea())).build();
    }

    public static Ong convertOngRequestPostDTOToOngEntity(OngRequestPostDTO ong){
        return Ong.builder().name(ong.getName())
                .cnpj(ong.getCnpj()).email(ong.getEmail()).address(ong.getAddress()).description(ong.getDescription()).urlImage(ong.getUrlImage()).phoneNumber(ong.getPhoneNumber())
                .actingArea(null).build();
    }

    public static Ong convertOngRequestPutDTOToOngEntity(OngRequestPutDTO ong){
        return Ong.builder().id(ong.getId()).name(ong.getName())
                .cnpj(ong.getCnpj()).email(ong.getEmail()).address(ong.getAddress()).description(ong.getDescription()).urlImage(ong.getUrlImage()).phoneNumber(ong.getPhoneNumber())
                .actingArea(null).build();
    }

    public static ActingAreaDTO convertActingAreaEntityToActingAreaDTO(ActingArea actingArea){
        return ActingAreaDTO.builder().id(actingArea.getId()).name(actingArea.getName()).build();
    }

    public static ActingArea  convertActingAreaDTOToActingAreaEntity(ActingAreaDTO actingAreaDTO){
        return ActingArea.builder().id(actingAreaDTO.getId()).name(actingAreaDTO.getName()).build();
    }
}
