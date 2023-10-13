package br.com.songs.services.file;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileDownloadService {
    Resource getFileAsResource(String fileCode) throws IOException;
}
