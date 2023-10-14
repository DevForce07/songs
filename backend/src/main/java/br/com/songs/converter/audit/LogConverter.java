package br.com.songs.converter.audit;

import br.com.songs.domain.audit.Logs;
import br.com.songs.web.dto.audit.LogDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogConverter {

    public static List<LogDTO> convertLogToDTO(List<Logs> logs){
        return logs == null ? new ArrayList<>():logs.stream().map(LogConverter::convertLogToDTO).collect(Collectors.toList());
    }
    public static LogDTO convertLogToDTO(Logs logs){
        return LogDTO.builder().id(logs.getId()).userName(logs.getUserName())
                .logSystem(logs.getLogSystem().toString()).dateTime(convertTimesTamp(logs.getCreatedOn()))
                .message(logs.getMessage()).build();
    }

    private static LocalDateTime convertTimesTamp(Instant instant){
        return instant == null ? LocalDateTime.now():instant.atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }
}
