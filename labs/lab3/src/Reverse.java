public class Reverse {

    public static int recursiveReverse(int num) {
        String numStr = String.valueOf(num);
        String revNumStr = recursiveReverseHelp(numStr);
        int revNum = Integer.parseInt(revNumStr);
        return revNum;
    }

    public static String recursiveReverseHelp(String numStr) {
        if (numStr.length() == 1) {
            return numStr;
        }
        else {
            String nextChar = String.valueOf(numStr.charAt(numStr.length()-1));
            String numSubstring = numStr.substring(0,numStr.length()-1);
            return nextChar + recursiveReverseHelp(numSubstring);
        }
    }

    public static int iterativeReverse(int num) {
        String numStr = String.valueOf(num);
        int numDigits = numStr.length();
        int revNum = 0;
        for (int i=1; i <= numDigits ; i++) {
            int nextChar = num % 10;
            revNum += nextChar * Math.pow(10, numDigits - i);
            num = num / 10;
        }
        return revNum;
    }

    public static void main(String[] args) {
        int revNum = recursiveReverse(1234);
        System.out.println(revNum);
    }
}
