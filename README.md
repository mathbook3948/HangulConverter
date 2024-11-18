# HangulConverter

`HangulConverter`는 한글 음절을 초성, 중성, 종성으로 분해하는 Java 유틸리티 클래스입니다. 이 클래스는 한글 음절을 분해하여 각 구성 요소를 추출하고, 특정 요소인 초성만 추출할 수 있는 메서드를 제공합니다.

## 기능

- **한글 분해**: 한글 음절을 초성, 중성, 종성으로 분해합니다.
- **초성 추출**: 주어진 한글 문자열에서 초성만 추출합니다.
- **예외 처리**: 잘못된 입력(예: 한글이 아닌 문자, null 값)에 대해 예외를 발생시킵니다.
- **한글만 처리**: 한글 문자만 처리하며, 공백이나 다른 문자는 무시합니다.

## 메서드

### `disassemble(String content)`
한글 문자열을 받아서 각 음절을 초성, 중성, 종성으로 분해한 후 2차원 배열로 반환합니다.

#### 파라미터:
- `content`: 분해할 한글 문자열입니다.

#### 반환:
- 각 한글 음절을 초성, 중성, 종성으로 분해한 2차원 배열을 반환합니다.
  예시: "가"는 `[["ㄱ", "ㅏ", ""]]`로 반환됩니다.

#### 예외:
- `NullPointerException`: 입력 문자열이 `null`인 경우 발생합니다.
- `IllegalArgumentException`: 입력 문자열에 한글이 아닌 문자가 포함된 경우 발생합니다.

### `getChoseong(String content)`
한글 문자열에서 초성만 추출하여 1차원 배열로 반환합니다.

#### 파라미터:
- `content`: 초성을 추출할 한글 문자열입니다.

#### 반환:
- 각 한글 음절에 해당하는 초성만을 포함한 1차원 배열을 반환합니다.
  예시: "가나다"는 `["ㄱ", "ㄴ", "ㄷ"]`을 반환합니다.

#### 예외:
- `NullPointerException`: 입력 문자열이 `null`인 경우 발생합니다.
- `IllegalArgumentException`: 입력 문자열에 한글이 아닌 문자가 포함된 경우 발생합니다.

## 사용 예시

```java
public class Main {
    public static void main(String[] args) {
        String text = "가나다";
        
        // 텍스트 분해
        String[][] disassembled = HangulConverter.disassemble(text);
        for (String[] parts : disassembled) {
            System.out.println("초성: " + parts[0] + ", 중성: " + parts[1] + ", 종성: " + parts[2]);
        }

        // 초성만 추출
        String[] choseong = HangulConverter.getChoseong(text);
        System.out.println("초성: " + String.join(", ", choseong));
    }
}
