package ru.digitalhabbits.homework2.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;

class FileReaderImplTest {

    private FileReaderImpl fileReader;

    @BeforeEach
    public void prepare() {
        this.fileReader = new FileReaderImpl();
    }

    @Test
    void readLines() {
        var file1 = getFile("test_reader.txt");

        assertThat(fileReader.readLines(file1).collect(Collectors.toList()))
                .containsOnly(
                        "cdccfdbfeadebaee",
                        "bdacffcecdaaafdc",
                        "fdbdefbececbcbca"
                );

        var file2 = getFile("test.txt");

        assertThat(fileReader.readLines(file2).collect(Collectors.toList()))
                .hasSize(1000);
    }

    private File getFile(String name) {
        return new File(getResource(name).getPath());
    }
}