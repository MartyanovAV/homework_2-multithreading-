package ru.digitalhabbits.homework2.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LetterCountMergerImplTest {
    private LetterCountMergerImpl letterCountMerger;

    @BeforeEach
    public void prepare(){
        this.letterCountMerger = new LetterCountMergerImpl();
    }

    @Test
    void merge() {
        Map<Character, Long> testMap1 = Map.of(
                'a', 2L, 'c', 7L, '5', 1L
        );
        Map<Character, Long> testMap2 = Map.of(
                'a', 3L, 'c', 9L, '7', 1L
        );
        assertThat(letterCountMerger.merge(testMap1, testMap2)).containsOnly(
                entry('a', 5L),
                entry('c', 16L),
                entry('5', 1L),
                entry('7', 1L)
        );

        assertThat(letterCountMerger.merge(testMap1, new HashMap<>())).containsOnly(
                entry('a', 2L),
                entry('c', 7L),
                entry('5', 1L)
        );
    }
}