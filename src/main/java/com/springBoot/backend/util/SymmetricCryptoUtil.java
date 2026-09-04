package com.springBoot.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class SymmetricCryptoUtil {
    private static final Logger log = LoggerFactory.getLogger(SymmetricCryptoUtil.class);
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256; // 128 bit or 256 bit

    private SymmetricCryptoUtil() {}

    public static String decrypt(String keyFilePath, String encryptedText) throws Exception {
        SecretKeySpec secretKey = loadKey(keyFilePath);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes);
    }

    public static String encrypt(String keyFilePath, String plainText) throws Exception {
        SecretKeySpec secretKey = loadKey(keyFilePath);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static SecretKeySpec loadKey(String keyFilePath) throws Exception {
        Path path = Paths.get(keyFilePath);

        if (!path.isAbsolute()) {
            log.debug("📂 지정된 키 경로가 '상대경로'입니다.");
            log.debug("📍 현재 실행 위치(User Dir): {}", System.getProperty("user.dir"));
        }

        Path absolutePath = path.toAbsolutePath().normalize();
        log.info("🔑 키 파일 탐색 위치: {}", absolutePath);

        if (!Files.exists(absolutePath)) {
            throw new java.io.FileNotFoundException("키 파일을 찾을 수 없습니다: " + absolutePath);
        }

        byte[] keyBytes = Files.readAllBytes(absolutePath);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public static void generateKeyFile(String filePath) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();

        Files.write(Paths.get(filePath), secretKey.getEncoded());
        System.out.println("✅ 키 파일 생성 완료: " + filePath);
    }

    // =================================================================
    //  🛠️ 개발자 도구: 이 main 메서드를 실행해서 암호화된 문자열을 얻으세요!
    // =================================================================
    public static void main(String[] args) {
        try {
            // 1. 설정: 경로와 비밀번호를 입력하세요.
            String targetKeyFile = "src/main/resources/secret/key/db-key.key"; // 키 파일을 저장할(읽을) 경로
            String plainPassword = ""; // 실제 DB 비밀번호

            // 2. 키 파일이 없으면 새로 생성 (최초 1회만 실행)
            SymmetricCryptoUtil.generateKeyFile(targetKeyFile);


            // 암호화 수행
            String encrypted = SymmetricCryptoUtil.encrypt(targetKeyFile, plainPassword);
            String base64Encoding = Base64.getEncoder().encodeToString(encrypted.getBytes());
            System.out.println("\n--- 결과 확인 ---");
            System.out.println("평문: " + plainPassword);
            System.out.println("암호화됨: " + encrypted);
            System.out.println("암호화 + Base64 인코딩됨(YAML에 붙여넣기): " + base64Encoding);

            // 복호화 검증
            String base64Decoding = new String(Base64.getDecoder().decode(base64Encoding));
            String decrypted = SymmetricCryptoUtil.decrypt(targetKeyFile, base64Decoding);
            System.out.println("Base64 디코딩 검증: " + base64Decoding);
            System.out.println("복호화 검증: " + decrypted);

            if (plainPassword.equals(decrypted)) {
                System.out.println("✅ 검증 성공! 완벽합니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
