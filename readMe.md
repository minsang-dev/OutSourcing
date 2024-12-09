## 🍕🍕🍕 달려가요_Delivery_OutSourcing

달려가요 는 우리가 흔히 이용하는 배달의 민족과 같은 배달 어플 서비스입니다. 평소 사용하던 경험을 바탕으로 프로젝트를 구현하였습니다.

## 🍗 Period : 24.12.03 ~ 24.12.06
## 🍔 기능
1. User
   - 회원가입
   - 인증인가를 이용한 로그인 CRUD (일반 유저/ 사장님)
2. Store
   - 사장님 권한을 가진 유저만 가게 생성
   - 폐업 상태가 아닌 가게 최대 3개까지만 운영
   - 가게 단건 조회 시 메뉴 목록 조회
   - 가게 폐업 시, 폐업 상태로 변경
3. Menu
   - 사장님 권한을 가진 유저만 메뉴 생성, 수정
   - 사장님은 본인 가게에만 메뉴 등록
   - 가게 조회 시, 메뉴 조회
   - 본인 가게의 메뉴만 삭제
   - SoftDelete를 이용한 메뉴상태만 삭제 상태로 변경
4. Order
   - 고객의 메뉴 주문 (각 주문에는 하나의 메뉴만 주문)
   - 사장님은 주문 수락 -> 배달 완료될 때까지의 모든 상태 보임
     - 주문완료 -> 주문 수락 -> 조리 중 -> 조리 완료 -> 배달 중 -> 배달 완료
   - 최소 주문 금액 만족
   - 가게 오픈/마감 시간 지나면 주문 불가
5. Review
   - 리뷰 생성 (별점 부여)
   - 가게 정보 기준으로 다건 조회 가능 
     - 생성일자 기준 최신순으로 정렬
     - 별점에 따른 조회
## 🍟 트러블 슈팅
1. User
- 권한 별로 필터를 구현 할 때 같은 api주소로 요청이 들어오는 것에 대하여 어떤 처리를 해야하는지 고민이 많았는데, 요청 방식(method)별로 구분할 수 이싸는 방법을 통하여 구현할 수 있었습니다.

2. Order 
- 테이블명으로 설정했던 "order"가 MySql에서 예약어로 등록되어 있어서 처음 테이블 생성 때 오류가 발생했었습니다. 원인을 파악한 후 MySql에서 제공한 예약어 문서에서 현재 우리 프로젝트와 겹치는 단어들을 찾고, 해당 단어들을 백틱(`)으로 감싸서 해결했습니다.

3. Review
- 대댓글을 조회하는 부분에서 JPQL을 활용하려고 하였는데 쿼리문이 복잡해지고, 댓글에 대댓글을 연결하는 부분의 구현이 아쉬웠습니다.

4. Exception
- @RestControllerAdvice는 사용해보아서 구현에 어려움이 없었지만 필터에서 반환하는 오류 메시지가 JSON형이아니라는 문제가 있었습니다.
  필터에서 예외 메시지를 받아 JSON형식으로 반환해주어 예외 메시지를 통일 할 수 있었습니다.

## 🥩 느낀점

- ### 김민상
  - 두번째 팀 프로젝트라 많은 기대를 하고 있었는데, 그동안 배운 것들을
    적용하고 응용할 수 있어 좋은 기회였던 거 같습니다. 또, 일상생활에서 이용하는 배달 어플이라는 주제로 개발을 하게 되어 흥미로웠던 것 같습니다. 함께 열심히 프로젝트를 진행 해주었던 팀원들에게도 많은 것을 배울 수 있어 좋았습니다.
- ### 이서준
  - 열심히 참여하여 좋은 결과를 얻을 수 있었고,이번 팀프로젝트를 통해 많은 것을 배울 수 있었습니다. 또한 깃허브를 통한 협업에 더 익숙해지는 기회가 되었고, 팀원분들이 많이 도와 주셔서 많은 것을 배울 수 있었습니다. 다들 고생 많으셨습니다!
- ### 김민
  - 이번 프로젝트에서는 깃허브의 프로젝트 보드 기능을 사용해본 게 큰 도움이 되었습니다. 말로 오가는 것도 소통이지만, 앱을 이용하여 전체적인 업무 흐름을 관리해보는 경험은 특별했습니다. 팀원분들 덕분에 많은 걸 얻어갑니다. 감사합니다!
- ### 김현준
  - Github 프로젝트를 통한 작업 관리 방식이나 프로젝트의 패키지 구조 등 시도해본 적 없는 것들을 경험할 수 있어 좋았습니다. 팀원들 덕분에 사용해보지 못했던 Spring, jpql 등을 사용해보며 배울 수 있었고 프로젝트를 무사히 마무리 지을 수 있었습니다. 감사합니다!
## 🥪 ERD
![img.png](images%2Fimg.png)