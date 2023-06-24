import java.util.Random;

public class Encoder {
    private static final int MAX_LENGTH = 11;

    public Encoder() {
        super();
    }

    private String convertHexToString(String hexString) {
        StringBuilder sb = new StringBuilder();
        int length = hexString.length();
        for (int i = 0; i < length - 1; i += 2) {
            String hex = hexString.substring(i, i + 2);
            int decimal = Integer.parseInt(hex, 16);
            decimal = decimal ^ 0xFF;
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    private String convertStringToHex(String input) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = input.toCharArray();
        for (char ch : charArray) {
            int decimal = ch ^ 0xFF;
            String hex = Integer.toHexString(decimal);
            sb.append(hex);
        }
        return sb.toString();
    }

    private byte[] getSalt() {
        byte[] salt = new byte[6];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }

    public String decode(String input) {

        StringBuilder sb = new StringBuilder();
        int length = input.length();
        int index = 0;

        while (index < length) {
            String hex = input.substring(index, index + 2);
            int decimal = Integer.parseInt(hex, 16);
            decimal = decimal % 4 - 4;

            int endIndex = index + 5;
            if (endIndex > length) {
                endIndex = length;
            }
            StringBuilder temp = new StringBuilder();
            int startIndex = index + 1;
            int endIndexWithOffset = index + 2 + decimal;

            if (startIndex <= endIndexWithOffset) {
                temp.append(input.substring(startIndex, endIndexWithOffset));
            }
            sb.append(temp.toString());
            index += 5;
        }

        String hexString = sb.toString();
        String result = convertHexToString(hexString);
        return result;
    }


    public String encode(String input) {
        if (input.length() !=11) {
            System.out.println("Input error!");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(input);
        sb.append("a");
        String hexString = convertStringToHex(sb.toString());
        StringBuilder result = new StringBuilder();
        byte[] salt = getSalt();
        int index = 0;
        int length = hexString.length();
        while (index < length) {
            
            int saltIndex = index / 4;
            int decimal = salt[saltIndex] % 4;
            String hex = Integer.toHexString(salt[saltIndex]);
            result.append(hex);

            int startIndex = index + decimal;
            int endIndex = index + 4;
            if (startIndex < 0) {
                startIndex = 0;
            }
            if (endIndex > length) {
                endIndex = length;
            }

            result.append(hexString.substring(startIndex, endIndex));
            index += 4;
        }

        return result.toString();
    }
}
