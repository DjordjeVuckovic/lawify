package org.lawify.psp.crypto.shared.converters;

public class StatusConverter {
    public static String convertToStatus(String status){
        return switch (status) {
            case "pending" -> "Pending";
            case "paid" -> "Success";
            default -> "Failed";
        };
    }
}
