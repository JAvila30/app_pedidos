package com.example.cerdiexpress.utils.impl;

import com.example.cerdiexpress.utils.IGenerateID;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateIDImpl implements IGenerateID {

    private static final SecureRandom secureRandom = new SecureRandom();
    private final char[] NUMBER_POOL = "0123456789".toCharArray();
    private final char[] LETTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final int WORD_SIZE = 6;
    private final int NUMBER_SIZE = 9;
    private final String TEMPLATE = "CE-%s-%s";

    @Override
    public String generateId() {

        return String.format(TEMPLATE,
                generateRandomPattern(secureRandom,LETTER_POOL,WORD_SIZE),
                generateRandomPattern(secureRandom,NUMBER_POOL,NUMBER_SIZE));
    }

    private static String generateRandomPattern(final Random random, final char[] alphabet, final int size) {
        final double alphabet_length = (alphabet.length - 1);
        final int mask = (2 << (int) Math.floor(Math.log(alphabet_length) / Math.log(2))) - 1;
        final int step = (int) Math.ceil(1.6 * mask * size / alphabet_length);

        final StringBuilder idBuilder = new StringBuilder();

        while (true) {
            final byte[] bytes = new byte[step];
            random.nextBytes(bytes);

            for (int i = 0; i < step; i++) {
                final int alphabetIndex = bytes[i] & mask;

                if (alphabetIndex < alphabet.length) {
                    idBuilder.append(alphabet[alphabetIndex]);
                    if (idBuilder.length() == size) {
                        return idBuilder.toString();
                    }
                }
            }
        }
    }


}
