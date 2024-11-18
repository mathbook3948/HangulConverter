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
     * @return 초성, 중성, 종성으로 분해된 각 글자별 2차원 String 배열
     */
    public static String[][] disassemble(String content) {
        String[][] result = new String[content.length()][3];

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
}
