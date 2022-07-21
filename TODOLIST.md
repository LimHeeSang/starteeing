공부해야할것 : controller부분이랑(filter, intercepter, api...), 예외처리, // spring security(jwt token 인증 & 인가), Oauth 2.0 기반 인증(jwt token필요?)

친구 서비스(FriendService)
  1. 친구목록 조회
  2. 친구 요청, 재요청
  3. 친구 수락/거절
  4. 친구 삭제
  5. FriendsStatus = REQUEST, RESPONSE, ACCEPT, REJECT, DELETE
  
팀 서비스(TeamService)
  1. 팀 만들기
    1-1. 팀 인원 선택
    1-2. 해당 멤버의 친구목록(친추 상태가 Accept상태) 조회
    1-3. 조회 한 친구목록에서 인원 만큼 팀원 선택
    1-4. 만든 팀원
    1-5. 팀원들한테 팀 목록에도 해당 팀 추가
  
    