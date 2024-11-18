package hangul.hangul;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();

        // 한글 자모 분해
        String[][] result = Hangul.disassemble(inputString);

        // 각 문자를 UTF-8 형식으로 출력
        for (int i = 0; i < result.length; i++) {
            System.out.println("원본: " + inputString.charAt(i));
            System.out.println("초성: " + result[i][0]);
            System.out.println("중성: " + result[i][1]);
            System.out.println("종성: " + result[i][2]);
            System.out.println("-------------------");
        }
    }
}
