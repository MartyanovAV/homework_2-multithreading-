package ru.digitalhabbits.homework2.impl;

import com.google.common.collect.Streams;
import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.Map;
import java.util.stream.Collectors;


public class LetterCountMergerImpl implements LetterCountMerger {

    @Override
    public Map<Character, Long> merge(Map<Character, Long> first, Map<Character, Long> second) {
        return Streams.concat(first.entrySet().stream(), second.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Long::sum));
    }

}
