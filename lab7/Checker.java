public class Checker {
    private byte[] secret;

    public Checker() {
        super();
        secret = new byte[] {0x70, 0x64, 0x64, 0x44, 0x1f, 0x5, 0x72, 0x78};
    }

    private static byte charToByteAscii(char c) {
        return (byte) c;
    }

    //将输入字符串的字符str与 secret 数组中的值进行异或操作，对字符串进行检查，判断是否与预设的密钥匹配。如果匹配，则返回 true，否则返回 false。
    private boolean checkStr1(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            byte ascii = charToByteAscii(c);
            int result = ascii ^ (i * 0xb);
            if (result != secret[i]) {
                return false;
            }
        }
        return true;
    }

    //对输入字符串进行检查，要求输入字符串必须能够解析为一个整数，并且满足一定的条件：大于等于 1000，取模 16 结果为 0，取模 27 结果为 0，取模 10 结果为 8。如果输入字符串满足这些条件，则返回 true，否则返回 false。如果输入字符串无法解析为整数，则也返回 false。
    private boolean checkStr2(String str) {
        try {
            int num = Integer.parseInt(str);
            Integer value = Integer.valueOf(num);
            int intValue = value.intValue();

            if (intValue < 1000) {
                return false;
            }

            intValue %= 16;
            if (intValue != 0) {
                intValue = value.intValue() % 27;
                if (intValue != 0) {
                    intValue = value.intValue() % 10;
                    if (intValue != 8) {
                        return false;
                    }
                }
            }

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    //对输入字符串 str 进行长度检查，并将其拆分为两个子字符串，然后分别使用 checkStr1 和 checkStr2 方法对这两个子字符串进行检查，并根据检查结果返回相应的布尔值。只有当两个子字符串都满足特定条件时，函数才会返回 true，否则返回 false。
    public boolean check(String str) {
        final int length = 12;
        final int startIndex = 0;
        final int endIndex = 8;

        if (str.length() != length) {
            return false;
        }

        String str1 = str.substring(startIndex, endIndex);
        String str2 = str.substring(endIndex, length);

        boolean check1 = checkStr1(str1);
        boolean check2 = checkStr2(str2);

        return check1 && check2;
    }
}
