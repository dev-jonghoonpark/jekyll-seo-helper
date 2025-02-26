# Jekyll SEO Helper

Jekyll Markdown 포스트 파일에서 description과 thumbnail을 자동으로 생성하는 도구입니다.

## 동작 방식

1. Description 생성 
   - Spring AI를 활용하여 Markdown 파일의 내용을 분석하고 요약한 후 description을 생성합니다.
   
2. Thumbnail 생성
    - Picsum에서 랜덤 이미지를 가져옵니다.
    - YAML 메타데이터의 title 값을 읽어, 제목 텍스트가 포함된 썸네일 이미지를 생성합니다.
    - 리소스를 조합하여 thumbnail 을 생성합니다.

thumbnail 생성에는 Java AWT를 사용합니다.

## 실행 방법

### 1. JAR 파일 생성

Gradle을 사용하여 프로젝트를 빌드합니다.

```sh
gradle build
```

빌드가 완료되면 `build/libs/jekyll-seo-helper-0.0.1-SNAPSHOT.jar` 파일이 생성됩니다.

### 2. JAR 파일 위치 설정
생성된 JAR 파일을 Jekyll 프로젝트 루트 디렉토리에 이동시킵니다. (또는 심볼릭 링크를 사용해도 무방합니다.)

### 3. JAR 파일 실행
아래 명령어를 사용하여 프로그램을 실행합니다.

환경변수 OPENAI_API_KEY를 설정하고, 처리할 Markdown 파일 경로를 인자로 전달해야 합니다.

```sh
OPENAI_API_KEY=YOUR_OPENAI_API_KEY java -jar jekyll-seo-helper-0.0.1-SNAPSHOT.jar "/_posts/post1.md" "/_posts/post2.md" ...
```
- OPENAI_API_KEY: Spring AI를 사용하기 위한 OpenAI API 키입니다.
- 인자: 처리할 Markdown 파일 경로입니다. 여러 파일을 동시에 처리할 수 있습니다.

## 결과 예시

입력글: [[Spring] 스프링에서 jwt를 이용한 인증시스템 만들기](https://jonghoonpark.com/2025/02/22/creating-an-authentication-system-with-jwt-in-java-spring)

### 생성된 description

> 스프링에서 JWT(Json Web Token)를 이용한 인증 시스템 구축 방법을 소개합니다. 이 글에서는 세션 토큰 방식과 JWT의 차이점을 설명하고, JWT의 구조, 대칭 및 비대칭 키 암호화 방식, 그리고 실제 구현 방법을 다룹니다. 또한, Spring Security와 의 통합 방법을 통해 JWT 기반 인증을 설정하는 방법도 안내합니다. JWT는 효율적이고 확장 가능한 인증 방식이지만, 보안상의 고려가 필요하므로 프로젝트 요구사항에 맞는 적절한 인증 방식을 선택하는 것이 중요합니다.

### 생성된 thumbnail

![thumbnail example](example.jpg)