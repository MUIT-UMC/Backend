# MUIT - Backend

## Branch Strategy : git flow
### 주요 브랜치
#### main
#### develop
#### feature
## 
### 브랜치 규칙
#### feature 브랜치
`develope`브랜치에서 분기하여 `feature/기능 이름` 으로 작성합니다.

e.g.) `feature/login`, `feature/common`

##### 작업이 완료되면 본인 브랜치에 push 후 `develop` 브랜치로 병합합니다
#### 커밋 메시지 컨벤션
`타입: 제목` 형식을 기본으로 하되 상황에 따라 유연하게 작성합니다.

`feat`: 새로운 기능 추가

`fix`: 오류 수

`doxs`: 문서 수정

`test`: 테스트 코드 추가 등

`chore`: 빌드 업무 수정 등 주요 코드 외 수정 사

----
## 작업 방법
### 1. git clone
`git clone [repository url]`
### 2. 본인 파트 작업
브랜치 생성: `git branch [본인 브랜치명] develop` (develop에서 분기)

브랜치 이동: `git checkout [본인 브랜치명]`

통합: `git checkout -b [본인 브랜치명] develop`
