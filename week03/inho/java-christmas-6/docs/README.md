# 메뉴
<애피타이저>
양송이수프(6,000), 타파스(5,500), 시저샐러드(8,000)

<메인>
티본스테이크(55,000), 바비큐립(54,000), 해산물파스타(35,000), 크리스마스파스타(25,000)

<디저트>
초코케이크(15,000), 아이스크림(5,000)

<음료>
제로콜라(3,000), 레드와인(60,000), 샴페인(25,000)

# 구현할 기능 목록

## 입력
- [x] 방문 날짜 입력 (숫자만)
- [x] 주문하실 메뉴를 메뉴와 개수
    - ex 티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1


## 출력
- [x] 실행 결과
    - [x] <주문 메뉴>
    - [x] <할인 전 총주문 금액>
    - [x] <증정 메뉴>
    - [x] <혜택 내역>
    - [x] <총혜택 금액>
    - [x] <할인 후 예상 결제 금액>
    - [x] <12월 이벤트 배지>

## 기능
- [x] 12월 1일 기준 1000원을 하루 지날 때마다 100원씩 증가 -> 25일 3400까지 할인.
- [x] 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
- [x] 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
- [x] 이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인
    - [x] 별은 3, 10, 17, 24, 25, 31
- [x] 할인 전 총주문 금액이 12만 원 이상일 때, 샴페인 1개 증정
- [x] 크리스마스 디데이 할인'을 제외한 다른 이벤트는 2023.12.1 ~ 2023.12.31 동안 적용
- [x] 금액에 따른 배지 부여
    - [x] 5천 원 이상: 별
    - [x] 1만 원 이상: 트리
    - [x] 2만 원 이상: 산타
- [x] 주의 사항
    - [x] 총주문 금액 10,000원 이상부터 이벤트가 적용됩니다.
    - [x] 음료만 주문 시, 주문할 수 없습니다.
    - [x] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다.
- [x] 개발 요청 사항
    - [x] 고객들이 식당에 방문할 날짜와 메뉴를 미리 선택하면 이벤트 플래너가 주문 메뉴, 할인 전 총주문 금액, 증정 메뉴, 혜택 내역, 총혜택 금액, 할인 후 예상 결제 금액, 12월 이벤트 배지 내용을 보여주기
    - [x] 날짜는 1 ~ 31 사이
        - [x] 범위를 벗어나면 "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 고객이 메뉴판에 없는 메뉴를 입력하는 경우, "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 메뉴의 개수는 1 이상의 숫자만 입력되도록 해주세요. 이외의 입력값은 "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 메뉴 형식이 예시와 다른 경우, "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 중복 메뉴를 입력한 경우(e.g. 시저샐러드-1,시저샐러드-1), "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 이벤트 배지가 부여되지 않는 경우, "없음"으로 보여 주세요.
    - [x] 증정 이벤트에 해당하지 않는 경우, 증정 메뉴 "없음"으로 보여 주세요.