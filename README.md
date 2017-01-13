# K-SPAM
봇 테러나 악성 유저를 사전에 차단할 수 있는 블랙리스트 플러그인입니다.

비동기 처리, 자체 캐싱으로 매우 가벼우며 사용에 부담이 없습니다.
(이벤트 패스 시간 약 0.001 초)

[ProtocolLib](https://dev.bukkit.org/projects/protocollib/files) 사용시 더 가볍고 빠르게 차단이 가능합니다.

[ProtocolLib 1.5.2](https://dev.bukkit.org/projects/protocollib/files/712442/download)
[ProtocolLib 1.7.2](https://dev.bukkit.org/projects/protocollib/files/785673/download)
[ProtocolLib 1.7.9](https://dev.bukkit.org/projects/protocollib/files/795545/download)

[ProtocolLib 1.8.x](https://dev.bukkit.org/projects/protocollib/files/888760/download)
[ProtocolLib 1.9.x](https://dev.bukkit.org/projects/protocollib/files/918909/download)
[ProtocolLib 1.10.x](https://dev.bukkit.org/projects/protocollib/files/945280/download)

[ProtocolLib 1.11.x](https://dev.bukkit.org/projects/protocollib/files/2358786/download)

명령어
----
> /kspam check 아이피/닉네임

해당 닉네임 또는 아이피의 스팸 여부를 확인합니다.

> /kspam force

강제 모드를 온/오프합니다. 강제모드가 활성화됐을 경우 그 시점 부터 처음 접속하는 유저들을 즉시 스팸 목록에 추가하며 입장을 거부합니다.

> /kspam remove 아이피/닉네임

해당 아이피나 닉네임을 스팸 목록에서 제거합니다. 닉네임의 경우 접속된 플레이어만 가능하며 오프라인일 경우 IP 를 입력하셔야 합니다.

> /kspam info

플러그인의 정보를 확인합니다.

> /kspam firstkick

처음 접속한 유저의 일회성 추방 기능을 온/오프합니다.

> /kspam debug

디버그 모드를 온/오프합니다.

설정 파일
------
> debug-mode: false

디버그 모드를 온/오프합니다. 디버그 모드가 활성화 됐을 경우 각 객체들의 작동 로그를 확인할 수 있습니다.

기본값은 비활성화입니다.

> first-login-kick: true

서버에 처음 접속한 유저를 1 회 킥합니다. 해당 옵션은 봇테러 방지를 위한 기능입니다.

기본값은 활성화입니다.

> alert: false

내부에서 생기는 예외를 알려주는 기능을 온/오프합니다.

기본값은 비활성화입니다.

> gc-period: 6

내부의 임시 캐시 데이터를 설정한 주기마다 청소합니다.

기본값은 6 시간입니다.

버그 제보
-----
Issue 메뉴를 클릭 후 New Issue 버튼을 눌러 제목과 내용을 작성해주시면 됩니다.

개발 참여/기여
----
K-SPAM 은 오픈소스 프로젝트로 누구나 개발에 참여할 수 있습니다. 방법은 우측 상단의 Fork 버튼을 눌러 자신의 저장소로 복사한 후 코드를 수정하셔서 Pull request 메뉴, New pull request 버튼을 눌러 내용을 작성해 전송해주시면 됩니다. 

프로그래밍에 익숙하지 않으신 분이라도 의욕만 있다면 환영입니다. 요청한 코드에 버그가 있을 경우 코멘트를 달아 알려드리며 그때 수정해주시면 됩니다.

자신의 API 를 사용하는 Checker 를 추가하고 싶다면 checker 패키지에 SpamChecker 를 상속해 알맞는 Result 를 반환하게 코드를 작성한 후
원하는 프로세서 생성자의 super.setCheckerList() 안에 체커를 추가하시면 됩니다.

동기로 돌아가는 프로세서, 가벼운 작업 위주
> SyncLoginProcessor, SyncJoinProcessor

비동기로 돌아가는 프로세서, 무거운 작업 위주
> AsyncLoginProcessor

클래스 구조 다이어그램
----
~~한국에 이보다 잘만든 플러그인이 있나요~~

![alt tag](https://github.com/EntryPointKR/K-SPAM/blob/master/diagram.png)

예정: 트랜잭션 패킷 체크