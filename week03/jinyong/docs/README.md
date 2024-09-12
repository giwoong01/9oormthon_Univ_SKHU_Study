- 프로젝트를 진행하기에 앞서 다음과 같은 기능명세서를 먼저 간단하게 작성했습니다.
  1. 사용자 입장에서 자동을 전제로 복권을 생성한다.
      1. 복권 구입 금액 입력한다. (1장 당 1000원)
          1. 1000원 미만이거나 나눠 떨어지지 않을 경우 예외를 발생시킨다.
      2. 장당 로또 번호 1~45를 임의로 6개 생성한다.
      3. 오름차순 정렬한다.
  2. 1~45까지 당첨 번호를 중복없이 입력한다.
      1. 6개의 번호와 보너스 번호 1개를 입력해야 한다.
          1. 공백, 각 번호 간 쉼표, 1~45 숫자가 아닌 경우에 대한 예외를 발생시킨다.
          2. 보너스 번호는 앞선 6개 번호와 중복돼서는 안된다. (에러 발생)
  3. 각 복권의 번호가 실제 당첨 번호에 포함돼 있는지 확인한다.
      1. 당첨 결과를 출력한다.
      2. 당첨금액과 복권 구입 금액을 가지고 수익률을 출력한다.
          1. 수익률은 소수점 둘째자리까지다.
  4. 각 등수 출력을 위한 Enum 클래스를 생성한다.
      1. 당첨 금액과 등수를 정의한다.

  - 강의에서 배웠던 MVC 패턴을 적용했습니다. 다음은 패키지 구조와 각 클래스의 역할입니다.
    ```
    ─src
    ├─main
    │  └─java
    │      └─lotto
    │          │  Application.java
    │          │
    │          ├─controller
    │          │      LottoController.java : model 처리 및 view 호출 - 컨트롤 역할
    │          │
    │          ├─model
    │          │      Lotto.java : 로또 당첨 번호 관리
    │          │      LottoAmount.java : 구입한 복권 갯수 관리
    │          │      LottoResultManager.java : 당첨번호와 비교, 결과 도출 및 수익률 계산
    │          │      RandomNumbers.java : 1~45까지의 숫자를 랜덤으로 생성
    │          │      Rank.java : 당첨 등수 관리 Enum
    │          │
    │          └─view
    │                  Exceptions.java : 에러 메시지 Enum
    │                  InputView.java : 사용자 입력 관리
    │                  OutputView.java : 출력 관리
    │
    └─test
      └─java
        ├─lotto
        │      ApplicationTest.java
        │      LottoTest.java
        │
        ├─model
        │      LottoAmountTest.java 
        │      LottoResultManagerTest.java
        │      RandomNumbersTest.java
        └─view
               InputViewTest.java
    ```

  - 구현하는 과정에서 각 로또의 당첨등수 여부를 어떻게 저장할지에 대해서 고민했습니다.
    - `Array`에 저장했을 때 `1|2|3|4|5|6` 와 같이 각 인덱스가 등수로 취급되고, 그 요소에 `count`가 저장하는 방식으로 생각했습니다. 다만 Rank를 Enum으로 만들었기 때문에 앞선 방식은 이 Enum 클래스를 온전히 활용하지 않는다고 판단했습니다.
    - 따라서 고민 끝에 Key-Value로 데이터를 저장하는 `Map`으로 구현하기로 결정했습니다. 다만 Map은 키의 중복을 허용하지 않으나 저장순서는 유지하지 않기 때문에 이 부분은 보완해주는 `LinkedHashMap`을 사용했습니다. `LinkedHashMap`은 null 값을 허용하고, `HashMap`의 장점을 상속하기에 이에 포함된 메소드의 시간복잡도는 O(1)입니다. 혹시 똑같은 부분을 고민하시는 분들은 한 번 써보시길 바랍니다.
  - Stream을 적극적으로 사용해서 코드를 간결하게 작성하려고 노력했습니다.
    - 그러나 예외처리에 곤란한 점이 있었습니다.
    ```
    public static List<Integer> stringToNumbers(String input) {
        String[] numbers = input.split(",");
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).boxed().toList();}
    ```
    - 위 코드는 입력받은 로또 번호를 Integer 타입의 List로 반환하는 메소드입니다. 위와 같은 방식도 1,2,3과 같은 값이 아니라 1,a,ㄷ처럼 숫자가 아닌 문자가 입력될 수 있습니다. 물론 저 상태로도 예외가 발생됩니다! 그러나 개발자가 알리고자 하는 메세지 혹은 의도한 동작을 추가하기 위해서는 적절한 코드가 아니라고 생각했습니다.
    ```
    public static int convertToIntegerElement(String number) {
        try {        
           return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            Exceptions.msgOnlyInputNumber();
            throw new IllegalArgumentException();
        }}
    ```
    - 그래서 각 리스트 요소를 하나하나 Integer로 변경하면서 예외 발생 시에 의도한 에러메세지를 출력하도록 했습니다.