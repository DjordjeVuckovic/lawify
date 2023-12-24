package org.lawify.psp.crypto.shared.crypto.hash;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashService {
    private final PasswordEncoder passwordEncoder;
    private final FixedSaltHashService fixedEncoder;
    public String hash(String plainValue) {
        return passwordEncoder.encode(plainValue);
    }
    public String hashFixed(String plainValue) {
        return fixedEncoder.encode(plainValue);
    }
    public boolean verifyFixed(String plainValue, String hashedValue) {
        return fixedEncoder.matches(plainValue, hashedValue);
    }

    public boolean verify(String plainValue, String hashedValue) {
        return passwordEncoder.matches(plainValue, hashedValue);
    }
}
