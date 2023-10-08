package br.com.songs.services.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@Data
public class FileDownloadServiceImpl implements FileDownloadService {
    @Override
    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("Files-Upload");
        final Path[] foundFile = {null};

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile[0] = file;
                return;
            }
        });

        if (foundFile[0] != null) {
            return new UrlResource(foundFile[0].toUri());
        }

        return null;
    }
}
