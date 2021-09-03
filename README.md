![app1](image_src/app.png)


## Fourinone App


### 만들게 된 동기
 1. 대학 동기들과 열품타 어플을 이용하던 중 MeMo, D-day 기능이 아쉽다고 느낌. 개선하여 사용하면 편할 것이라고 생각.
2. 열품타에서의 문제점은 공부 타이머를 킨 상태에서 오늘 할일을 미리 적어놓은 메모장을 보기가 생각보다 번거로움. 그 지점에서 시작하여 매일 계획한 일과 주나 월 단위로 해야하는 일을 한개의 어플로 관리하고 취침 전에 정리를 할 수 있다면 편리할 것이라고 생각했다.

### 개발 방향과 시행착오
1. navigation과 Fragment 기능을 이용하여 Home_fragment에서 4분할로 D-day Calendar, MemoPad, StopWatch, Diary 의 미리보기 화면이 보이게 함.
2. 각 Fragment의 Layout(혹은 Button)을 클릭 시 각각의 메인 Fragment로 이동. 더 크고 관리하기 쉬운 화면으로 이용 가능하게.

    -- Home_Fragment에서 다른 Fragment로 이동 후 다시 Fragment를 종료할때 Fragment의 생명주기가 끝나버리는 문제 발생.

    **Fragment의 생명주기 함수를 override하여 onStop()이나 onDestory()의 시기를 바꾸는 방법 등을 찾아봐야 할듯.

1. activity_main.xml 에서 Layout을 4분할하여 4개의 Activity의 Layout(혹은 Button)을 눌렀을 때 해당 Layout이 기기의 전체를 덮도록 해보자.
    -- ImageView 를 확대하는 방법 밖에 찾지 못했다. 더 찾아봐야할 듯.

### ++ 이외 추가해야할 사항
D-day Calendar 앱을 왼쪽 상단에 추가. 
오른쪽 상단 NotePad에 편집 기능 추가, 작성 시 더 편리하게 만들기(작성 공간을 늘리고 텍스트 옆에 작은 버튼을 두어 클릭시 해당 텍스트에 취소선 긋기 등).
 오른쪽 하단에 Diary에는 StopWatch에서 공부한 시간과 NotePad에서 마무리한 일(취소선 그은 일)들이 자동으로 업데이트되도록 추가. 
현재 익명 Auth으로 되어있는 것을 FireBase 등을 이용해 Google 연동, 허용한 친구들이 허용한 범위만큼 공유할 수 있도록 하기

