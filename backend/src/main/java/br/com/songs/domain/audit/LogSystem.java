package br.com.songs.domain.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum LogSystem {
    LOGIN("Login system"),
    UPDATE_ONG("update ong"),
    CREATE_ONG("create ong"),
    DELETE_ONG("delete ong"),
    UPDATE_ADMIN("update admin"),
    DELETE_ADMIN("delete admin"),
    CREATE_ADMIN("create admin"),
    UPDATE_EMPLOYEES("update employees"),
    DELETE_EMPLOYEES("delete employees"),
    CREATE_EMPLOYEES("create employees"),
    UPDATE_VACANCIES("update vacancies"),
    CREATE_VACANCIES("create vacancies"),
    DELETE_VACANCIES("delete vacancies");

    private String message;
}
