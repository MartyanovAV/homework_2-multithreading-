package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileReader;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.LetterCounter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class AsyncFileLetterCounter implements FileLetterCounter {

    private final Executor executor;
    private final FileReader fileReader;
    private final LetterCounter letterCounter;
    private final LetterCountMerger letterCountMerger;

    public AsyncFileLetterCounter() {
        this.executor = Executors.newWorkStealingPool();
        this.fileReader = new FileReaderImpl();
        this.letterCounter = new LetterCounterImpl();
        this.letterCountMerger = new LetterCountMergerImpl();
    }

    public AsyncFileLetterCounter(Executor executor, FileReader fileReader, LetterCounter letterCounter, LetterCountMerger letterCountMerger) {
        this.executor = executor;
        this.fileReader = fileReader;
        this.letterCounter = letterCounter;
        this.letterCountMerger = letterCountMerger;
    }

    @Override
    public Map<Character, Long> count(File input) {
        try (Stream<String> lines = fileReader.readLines(input)) {
            return lines.map(str -> CompletableFuture.supplyAsync(() -> letterCounter.count(str), executor))
                    .reduce(
                            CompletableFuture.completedFuture(new HashMap<>()),
                            (val1, val2) -> val1.thenCombineAsync(val2, letterCountMerger::merge, executor)
                    )
                    .join();
        }
    }

}
