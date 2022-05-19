# main-proj-BE



### 1. 이슈등록
  ![image](https://user-images.githubusercontent.com/53857239/168947013-797d54c2-e577-49bf-9a39-7a7ba9e596cc.png)
- 기능일 경우 feat: ~~~  
- 버그관련은  bug: ~~~  
- 리팩토링은  refactor: ~~~  
- 그 외 자잘한 수정사항 chore: ~~~  

> 제목은 잘 고민해서 통일하기!

### 2. 브랜치 생성  
  
2-1. 현재 가지고 있는 **브랜치 확인**하기
> 현재 가진 브랜치를 확인하는 커맨드  
> git branch  

2-2. 원격 저장소에 있는 **브랜치 가져오기**  
> git checkout -t origin/develop  
> 또는   
> git checkout -f -t origin/develop
- -f 옵션을 사용하면 문제가 발생해도 브랜치를 가져올 수 있습니다.
- 기본적으로 branch를 가져오려는데 로컬에 같은 이름의 브랜치가 있으면 충돌합니다.  
  때문에 같은이름의 브랜치가 있으면 아래 명령어로 로컬 브랜치를 삭제해주어야합니다.
> git branch -d [브랜치 명]  

![image](https://user-images.githubusercontent.com/53857239/168986629-971fd45f-29f6-411a-bdbf-7d3648531560.png)
> git branch -r  
옵션을 통해 리모트에 있는 브랜치를 확인할 수 있습니다.

![image](https://user-images.githubusercontent.com/53857239/169193241-2e6b9436-e86b-4510-a2ea-56c5d0bdcecd.png)
> git checkout -t origin/develop  
명령어로 브랜치를 가져오고 develop 브랜치로 이동된것을 확인할 수 있습니다.

2-3. 내가 작업할 **로컬 브랜치 생성**하기  
브랜치명은  분류/작업명 으로 통일합니다  
ex) feature/login (feature로 쓸지 feat로 줄여쓸지는 이야기 해봅시다.)  

지금 작업은 간단한 문서화이기 때문에 docs/git-flow 로 하겠습니다.

![image](https://user-images.githubusercontent.com/53857239/169195366-13200a4c-a0c2-467c-9375-08cd91c3d429.png)
> git branch [생성하려는 브랜치명] [분기기분이 되는 브랜치명]  
로 브랜치를 생성할 수 있습니다.

2-4. 작업 후 원격저장소에 브랜치 푸시하기
> develop 브랜치에 내가 작업한 내용을 푸시하고 싶다면 2가지 방법이 있습니다.
1. 내가 작업한 브랜치를 원격저장소에 푸시한 후, PR을 날린다.
2. 내 로컬저장소에서 develop 브랜치에 merge한 후, PR을 날린다.

당연히 1번으로 해야겠죠?

