package hangul.hangul;
//https://github.com/intotherealworld/jamo 참고했음

import java.util.ArrayList;
import java.util.List;

public class HangulConverter {

    private static final int HANGUL_SYLLABLE_BASE = 0xAC00;
    @SuppressWarnings("unused")
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
     * 한글 문자열을 받아서 초성, 중성, 종성으로 글자별로 분해하여 2차원 배열로 반환한다.
     *
     * <p>
     * 이 메서드는 한글 음절만 처리하며, 한글 외 문자가 포함된 경우 {@link IllegalArgumentException}을
     * 발생시킨다. 또한 입력 문자열에 공백(띄어쓰기)이 포함된 경우 빈 2차원 배열을 반환한다.</p>
     *
     * @param content 분해할 한글 문자열
     * @return 초성, 중성, 종성으로 분해된 각 글자별 2차원 배열. 예를 들어, "가"는 [["ㄱ", "ㅏ", ""]]로
     * 분해된다. 공백이 포함된 경우 빈 배열 반환.
     * @throws NullPointerException 입력 문자열이 {@code null}인 경우
     * @throws IllegalArgumentException 입력 문자열이 한글 음절이 아닌 문자를 포함하는 경우
     */
    public static String[][] disassemble(String content) throws NullPointerException {
        if (content == null) {
            throw new NullPointerException("입력 값은 null이 될 수 없습니다");
        }
        if (content.contains(" ")) {
            return new String[0][];
        }
        if (!isHangul(content)) {
            throw new IllegalArgumentException("입력 문자열은 한글만 포함해야 합니다");
        }
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

    /**
     * 한글 문자열에서 초성만 추출하여 1차원 배열로 반환한다.
     *
     * <p>
     * 이 메서드는 한글 음절만 처리하며, 한글 외 문자가 포함된 경우 {@link IllegalArgumentException}을
     * 발생시킨다. 또한 입력 문자열에 공백(띄어쓰기)이 포함된 경우 빈 배열을 반환한다.</p>
     *
     * @param content 초성을 추출할 한글 문자열
     * @return 문자열의 각 한글 음절에 해당하는 초성만을 포함한 1차원 배열. 예를 들어, "가나다"는 ["ㄱ", "ㄴ",
     * "ㄷ"]를 반환한다. 공백이 포함된 경우 빈 배열 반환.
     * @throws NullPointerException 입력 문자열이 {@code null}인 경우
     * @throws IllegalArgumentException 입력 문자열이 한글 음절이 아닌 문자를 포함하는 경우
     */
    public static String[] getChoseong(String content) throws NullPointerException {
        if (content == null) {
            throw new NullPointerException("입력 값은 null이 될 수 없습니다");
        }
        if (content.contains(" ")) {
            return new String[0];
        }
        if (!isHangul(content)) {
            throw new IllegalArgumentException("입력 문자열은 한글만 포함해야 합니다");
        }
        String[] result = new String[content.length()];

        String[][] disassembled = disassemble(content);

        int i = 0;
        for (String[] strings : disassembled) {
            result[i++] = strings[0];
        }
        return result;
    }

    /**
     * 주어진 문자열이 한글 음절, 자모로만 구성되어 있는지 확인합니다.
     * <p>
     * 이 메서드는 입력 값이 null인 경우 {@link NullPointerException}을 발생시킵니다.
     * <p>
     * 한글 음절(가-힣), 자모(ㄱ-ㅎ, ㅏ-ㅣ)만 허용하며, 그 외의 문자(공백 포함)가 포함되면 false를 반환합니다.
     *
     * @param content 확인할 문자열
     * @return 한글 음절, 자모, 공백만 포함된 문자열이면 true, 그 외의 문자가 포함되면 false
     * @throws NullPointerException 입력 값이 null일 경우 발생
     */
    public static boolean isHangul(String content) throws NullPointerException {
        if (content == null) {
            throw new NullPointerException("입력 값은 null이 될 수 없습니다");
        }
        return content.matches("^[\\u1100-\\u11FF\\u3130-\\u318F\\uAC00-\\uD7AF]*$");
    }

    /**
     * 주어진 단어가 타겟 문자열에 포함되어 있는지 확인합니다.
     * <p>
     * 이 메서드는 타겟 문자열과 단어 문자열을 각각 초성, 중성, 종성으로 분해한 후, 그 자모를 순차적으로 비교하여 "word"가
     * "target"에 포함되어 있는지 확인합니다. 공백은 무시되고, 입력된 문자열이 한글로만 구성되어 있어야 합니다.
     * </p>
     *
     * @param word 확인할 단어 문자열. 한글만 포함되어야 하며, 공백은 허용되지 않습니다.
     * @param target 포함 여부를 확인할 대상 문자열. 한글만 포함되어야 하며, 공백은 허용되지 않습니다.
     * @return {@code true} if the word is contained within the target,
     * {@code false} otherwise.
     * @throws NullPointerException 만약 입력된 {@code word}나 {@code target}이
     * {@code null}인 경우 발생합니다.
     * @throws IllegalArgumentException 만약 입력된 {@code word}나 {@code target}이 한글이
     * 아닌 문자를 포함하는 경우 발생합니다.
     */
    public static boolean containsHangulWord(String word, String target) {

        String[][] target_disassembled = disassemble(target.replaceAll("\\s", ""));
        String[][] word_disassembled = disassemble(word.replaceAll("\\s", ""));
        List<String> target_array = new ArrayList<>();
        List<String> word_array = new ArrayList<>();

        // target 단어를 분해하여 리스트에 추가
        for (String[] target_disassembled1 : target_disassembled) {
            for (String item : target_disassembled1) {
                if (item.equals("") || item.equals(" ")) {
                    continue;
                }
                target_array.add(item);
            }
        }

        // word 단어를 분해하여 리스트에 추가
        for (String[] word_disassembled1 : word_disassembled) {
            for (String item : word_disassembled1) {
                if (item.equals("") || item.equals(" ")) {
                    continue;
                }
                word_array.add(item);
            }
        }

        // List의 내용만을 비교하는 방법
        StringBuilder targetStr = new StringBuilder();
        for (String item : target_array) {
            targetStr.append(item);
        }

        StringBuilder wordStr = new StringBuilder();
        for (String item : word_array) {
            wordStr.append(item);
        }

        // "word"가 "target"에 포함되어 있는지 확인
        return targetStr.toString().contains(wordStr.toString());
    }

    /**
     * 한글 문자열을 비한글과 한글로 구분하여 배열로 반환한다.
     *
     * @param content 한글과 비한글(공백 포함)이 혼합된 문자열
     * @return 한글과 비한글로 구분된 배열
     */
    public static String[] seperateHangul(String content) {
        content = content.trim();
        String[] contents = content.split("");
        List<String> result = new ArrayList<>();
        String container = "";
        Boolean prevIsHangul = null;

        for (String c : contents) {
            boolean isCurrentHangul = isHangul(c);

            if (prevIsHangul == null) {
                prevIsHangul = isCurrentHangul;
                container = c;  // 첫 번째 문자는 무조건 container에 추가
            } else if (prevIsHangul == isCurrentHangul) {
                container += c;  // 한글 또는 비한글이 연속되면 이어서 추가
            } else {
                result.add(container);  // 연속되지 않으면 현재까지 담은 문자 저장
                container = c;  // 새로운 그룹의 첫 번째 문자로 시작
                prevIsHangul = isCurrentHangul;
            }
        }

        // 마지막 남은 문자열도 result에 추가
        if (!container.isEmpty()) {
            result.add(container);
        }

        return result.toArray(new String[0]);
    }

}
