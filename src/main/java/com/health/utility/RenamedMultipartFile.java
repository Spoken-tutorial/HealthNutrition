package com.health.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class RenamedMultipartFile implements MultipartFile {

    private final MultipartFile file;
    private final String newName;

    public RenamedMultipartFile(MultipartFile file, String newName) {
        this.file = file;
        this.newName = newName;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return newName;
    }

    @Override
    public String getContentType() {
        return file.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return file.isEmpty();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return file.getBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Path tempFile = Files.createTempFile("temp", ".tmp");
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        Files.move(tempFile, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}