package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileReader;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Stream;

public class FileReaderImpl implements FileReader {

    @Override
    public Stream<String> readLines(File file) {
        try {
            return Files.lines(file.toPath());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
