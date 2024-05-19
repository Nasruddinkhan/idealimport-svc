package ca.com.idealimport.common.util;

import ca.com.idealimport.service.saleorder.repository.SaleOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TracingNumberGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CHAR_LENGTH = 5;
    private static final int DIGIT_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final SaleOrderRepository saleOrderRepository;

    private static String generateTracingNumber() {
        final String charactersPart = IntStream.range(0, CHAR_LENGTH)
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()))))
                .collect(Collectors.joining());
        final String digitsPart = IntStream.range(0, DIGIT_LENGTH)
                .mapToObj(i -> String.valueOf(RANDOM.nextInt(10)))
                .collect(Collectors.joining());
        return charactersPart + digitsPart;
    }

    public String getSaleOrderTrackingNumber() {
        return Stream.generate(() -> generateTracingNumber())
                .filter(e -> !saleOrderRepository.findByTrackingId(e).isPresent())
                .findFirst()
                .orElseGet(TracingNumberGenerator.this::getSaleOrderTrackingNumber);
    }
}
