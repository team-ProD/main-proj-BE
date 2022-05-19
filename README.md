

### 1. 이슈등록
  ![image](https://user-images.githubusercontent.com/53857239/168947013-797d54c2-e577-49bf-9a39-7a7ba9e596cc.png)
- 기능일 경우 feat: ~~~  
- 버그관련은  bug: ~~~  
- 리팩토링은  refactor: ~~~  
- 그 외 자잘한 수정사항 chore: ~~~  

> 제목은 잘 고민해서 통일하기!

### 2. 브랜치 생성  및 푸시
  
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

![image](https://user-images.githubusercontent.com/53857239/169196903-f8e2ea3c-1996-4622-9910-51c4b6169df9.png)

> READEME.md 파일을 수정한 후 commit & push  
보니까 원격저장소엔 지금 제가 만든 브랜치가 없으니, 만들고 올리고 싶으면  
 git push --set-upstream origin docs/git-flow  
를 쓰라네요!


![image](https://user-images.githubusercontent.com/53857239/169197172-57e72054-657c-4d4a-a8b2-a35387c8e0e2.png)

> 하라는대로 하니 평화롭게 잘 올라가 주었습니다. 굿!

### PR 및 이슈 클로즈
![image](https://user-images.githubusercontent.com/53857239/169197290-7c1b52f0-e898-4321-9bdf-3db78fa78467.png)

> github 화면에 이런식으로 표시됩니다.

![image](https://user-images.githubusercontent.com/53857239/169197656-b5500de1-e447-4e36-b77f-2a0701499ff0.png)
> 이슈번호 적어주시고 내용 채워주시고~ 리뷰어 지정해주시고~  
    빨간 네모부분은 develop으로 바꿔줍시당.