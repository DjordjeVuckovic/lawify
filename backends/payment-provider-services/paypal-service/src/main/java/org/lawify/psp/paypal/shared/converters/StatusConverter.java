package org.lawify.psp.paypal.shared.converters;

public class StatusConverter {
    public static String ConvertToStatus(String status){
        return switch (status) {
            case "CREATED" -> "Pending";
            case "COMPLETED" -> "Success";
            default -> "Failed";
        };
    }
}
