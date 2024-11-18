package hangul.hangul;
//https://github.com/intotherealworld/jamo 참고했음

public class HangulConverter {

    private static final int HANGUL_SYLLABLE_BASE = 0xAC00;
    private static final int INITIALS_COUNT = 19;
    private static final int MEDIALS_COUNT = 21;
    private static final int FINALS_COUNT = 28;

    private static final char[] INITIALS = {
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    private static final char[] MEDIALS = {
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    };

    private static final char[] FINALS = {
        '\0', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    private static String[] disassemble(char syllable) {
        int syllableCode = syllable - HANGUL_SYLLABLE_BASE;

        int finalIndex = syllableCode % FINALS_COUNT;
        int medialIndex = (syllableCode / FINALS_COUNT) % MEDIALS_COUNT;
        int initialIndex = (syllableCode / (FINALS_COUNT * MEDIALS_COUNT));

        String finalChar = (finalIndex == 0) ? "" : String.valueOf(FINALS[finalIndex]);

        return new String[]{
            String.valueOf(INITIALS[initialIndex]),
            String.valueOf(MEDIALS[medialIndex]),
            finalChar
        };
    }

    /**
     * 한글 문자열을 받아서 초성, 중성, 종성으로 글자별로 분해하여 2차원 배열로 반환한다
     *
     * @param content 분해하기 위한 문자열
     * @return 초성, 중성, 종성으로 분해된 각 글자별 2차원 String 배열을 반환. 만약 문자열에 공백(띄어쓰기)이 있을 때
     * 빈 문자열을 반환
     * @throws NullPointerException 입력 문자열이 {@code null}인 경우
     */
    public static String[][] disassemble(String content) throws NullPointerException {
        String[][] result = new String[content.length()][3];
        if (content.contains(" ")) {
            return null;
        }

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= 0xAC00 && c <= 0xD7A3) {
                result[i] = disassemble(c);
            } else {
                result[i] = new String[]{"", "", ""};
            }
        }
        return result;
    }

    /**
     * 문자열을 받아 초성만 추출하여 1차원 배열로 반환
     *
     * @param content 초성을 추출할 문자열
     * @return 초성만 추출된 1차원 String 배열을 반환. 만약 문자열에 공백(띄어쓰기)이 있을 때 빈 문자열을 반환
     * @throws NullPointerException 입력 문자열이 {@code null}인 경우
     */
    public static String[] getChoseong(String content) throws NullPointerException {
        String[] result = new String[content.length()];

        if (content.contains(" ")) {
            return new String[0];
        }

        String[][] disassembled = disassemble(content);

        int i = 0;
        for (String[] strings : disassembled) {
            result[i++] = strings[0];
        }
        return result;
    }
}
