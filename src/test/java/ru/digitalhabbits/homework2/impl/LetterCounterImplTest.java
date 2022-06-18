package ru.digitalhabbits.homework2.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;


class LetterCounterImplTest {

    private LetterCounterImpl letterCounter;

    @BeforeEach
    public void prepare(){
        this.letterCounter = new LetterCounterImpl();
    }

    @Test
    void count() {

        assertThat(letterCounter.count("asdsafsaads")).containsOnly(
                entry('a', 4L),
                entry('s', 4L),
                entry('d', 2L),
                entry('f', 1L)
        );

        assertThat(letterCounter.count("")).isEmpty();

        assertThat(letterCounter.count("asdsafsa\nad\ts")).containsOnly(
                entry('a', 4L),
                entry('s', 4L),
                entry('\n', 1L),
                entry('\t', 1L),
                entry('d', 2L),
                entry('f', 1L)
        );
    }
}