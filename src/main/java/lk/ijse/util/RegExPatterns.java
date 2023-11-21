package lk.ijse.util;

import java.util.regex.Pattern;

public class RegExPatterns {
    private static final Pattern namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");
    private static final Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])(com)$");
    private static final Pattern doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
    private static final Pattern intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");
    private static final Pattern customerId = Pattern.compile("^C0[0-9]{2,}$");
    private static final Pattern vehicleId = Pattern.compile("^V0[0-9]{2,}$");
    private static final Pattern toolId = Pattern.compile("^T0[0-9]{2,}$");
    private static final Pattern employeeId = Pattern.compile("^E0[0-9]{2,}$");
    private static final Pattern supplierId = Pattern.compile("^Sup0[0-9]{2,}$");
    private static final Pattern contactPattern = Pattern.compile("^0[0-9]{9}$");
    private static final Pattern passwordPattern = Pattern.compile("^[0-9a-zA-Z]{5}$");
    private static final Pattern NICPattern = Pattern.compile("^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$");
    private static final Pattern contactNumberPattern = Pattern.compile("^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$");
    private static final Pattern addressPattern = Pattern.compile("[a-zA-Z]{15}");


    public static Pattern getContactNumberPattern() {
        return contactNumberPattern;
    }
    public static Pattern getAddressPattern() {
        return addressPattern;
    }


    public static Pattern getNICPattern() {
        return NICPattern;
    }

    public static Pattern getCustomerId() {
        return customerId;
    }
    public static Pattern getVehicleId() {return vehicleId;}

    public static Pattern getNamePattern() {return namePattern;}

    public static Pattern getDoublePattern() {
        return doublePattern;
    }

    public static Pattern getEmailPattern() {
        return emailPattern;
    }

    public static Pattern getIntPattern() {
        return intPattern;
    }

    public static Pattern getContactPattern() {
        return contactPattern;
    }

    public static Pattern getoolId() {
        return toolId;
    }

    public static Pattern getEmployeeId() {
        return employeeId;
    }


    public static Pattern getSupplierId() {
        return supplierId;
    }

    public static Pattern getPasswordPattern() {
        return passwordPattern;
    }
}
