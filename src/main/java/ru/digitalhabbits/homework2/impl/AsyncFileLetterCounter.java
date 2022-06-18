package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileReader;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.LetterCounter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//todo Make your impl
public class AsyncFileLetterCounter implements FileLetterCounter {

    private final ExecutorService executor;
    private final FileReader fileReader;
    private final LetterCounter letterCounter;
    private final LetterCountMerger letterCountMerger;

    public AsyncFileLetterCounter() {
        this.executor = Executors.newWorkStealingPool();
        this.fileReader = new FileReaderImpl();
        this.letterCounter = new LetterCounterImpl();
        this.letterCountMerger = new LetterCountMergerImpl();
    }

    public AsyncFileLetterCounter(ExecutorService executor, FileReader fileReader, LetterCounter letterCounter, LetterCountMerger letterCountMerger) {
        this.executor = executor;
        this.fileReader = fileReader;
        this.letterCounter = letterCounter;
        this.letterCountMerger = letterCountMerger;
    }

    //TODO calculating characters count and merging results should be run asynchronously
    @Override
    public Map<Character, Long> count(File input) {
        return fileReader.readLines(input)
                .map(str -> executor.submit(() -> letterCounter.count(str)))
                .map(feature -> {
                    try {
                        return feature.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .reduce(letterCountMerger::merge)
                .orElse(new HashMap<>());
    }
}
