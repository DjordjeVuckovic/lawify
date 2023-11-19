package org.lawify.psp.mediator.shared.crypto.utils;

public class HexKeyUtils {
    public static String bytesToHex(byte[] bytes){
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02X", b);
            hexStringBuilder.append(hex);
        }
        return hexStringBuilder.toString();
    }
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }
}
