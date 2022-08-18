
<img src="./img/logotitle.png" width="600px">

&nbsp;

프로젝트의 이름은 'QueÆnt' 로, 여왕개미(QueenAnt) 의 약자를 의미합니다.   
한국에서는 개인 투자자들을 개미라고 부르기도 하는데, 안정적인 투자를 통해 여왕개미가 되자는 뜻을 내포하고 있습니다.

최근, 금리가 올라가며 은행의 예,적금 상품에 관심이 많아졌습니다.
QueÆnt에서는 국내 은행들의 예,적금 상품들의 정보를 제공하고, 사용자 조건에 적합한 예,적금 상품을 추천함으로써 효율적으로, 그리고 효과적으로 자신의 포트폴리오를 구성해 수익률을 비교해볼 수 있는 서비스를 제공합니다.

&nbsp;


# 목차
1. 기획
2. 주요 기능
3. 기술 스택 & 버전
4. 개발 환경
5. 서비스 구조
6. 팀원 소개

&nbsp;

# 1. 기획

## ERD
<img src="./img/erd.jpg" width="600px">

&nbsp;

## 와이어프레임
<img src="./img/figma.jpg" width="600px">

&nbsp;

# 2. 주요 기능
- 국내 시중 은행 및 저축 은행의 예,적금 정보 제공
- 마이포트폴리오 및 가상포트폴리오를 생성하고 현금흐름 및 수익률 비교
- 자산관리 관련 뉴스와 컨텐츠 제공

&nbsp;

# 3. 기술 스택 & 아키텍처 &버전
<img src="./img/stacks.jpg" width="600px">

<img src="./img/architecture.png" width="600px">

<br>

## Version

### FE:
    "Node.js" : {

    "node.js" : "^16.16.0",
	"npm" : "^8.11.0"

    },

    "VSCode" : {

    "VSCode" : "^1.70.0",
	"Chromium" : "^100.0.4896.160",
	"node.js" : "^16.13.2",
	"V8" : "^10.0.139.17-electron.0",
	"OS" : "^Windows-NT x64 10.0.19044"

    },

    "Vue" : {

    "name": "queant",
    "version": "0.1.0",
    "private": true,
    "scripts": {
        "serve": "vue-cli-service serve",
        "build": "vue-cli-service build",
        "lint": "vue-cli-service lint"
    },
    "dependencies": {
        "@ckeditor/ckeditor5-build-classic": "^35.0.1",
        "@ckeditor/ckeditor5-vue": "^4.0.1",
        "@fortawesome/fontawesome-svg-core": "^6.1.2",
        "@fortawesome/free-brands-svg-icons": "^6.1.2",
        "@fortawesome/free-regular-svg-icons": "^6.1.2",
        "@fortawesome/free-solid-svg-icons": "^6.1.2",
        "@fortawesome/vue-fontawesome": "^3.0.1",
        "axios": "^0.27.2",
        "bootstrap": "^5.1.3",
        "bootstrap-vue": "^2.22.0",
        "core-js": "^3.8.3",
        "dotenv": "^16.0.1",
        "email-validator": "^2.0.4",
        "lodash": "^4.17.21",
        "node-sass": "^7.0.1",
        "password-validator": "^5.3.0",
        "sass-loader": "^13.0.2",
        "vue": "^3.2.13",
        "vue-loader": "^17.0.0",
        "vue-router": "^4.0.13",
        "vuex": "^4.0.2",
        "vuex-persistedstate": "^4.1.0"
    },
    "devDependencies": {
        "@babel/core": "^7.12.16",
        "@babel/eslint-parser": "^7.12.16",
        "@vue/cli-plugin-babel": "~5.0.0",
        "@vue/cli-plugin-eslint": "~5.0.0",
        "@vue/cli-plugin-router": "~5.0.0",
        "@vue/cli-service": "~5.0.0",
        "eslint": "^7.32.0",
        "eslint-plugin-vue": "^8.0.3",
        "webpack": "^5.73.0"
    },
    "eslintConfig": {
        "root": true,
        "env": {
        "node": true
        },
        "extends": [
        "plugin:vue/vue3-essential",
        "eslint:recommended"
        ],
        "parserOptions": {
        "parser": "@babel/eslint-parser"
        },
        "rules": {}
    },
    "browserslist": [
        "> 1%",
        "last 2 versions",
        "not dead",
        "not ie 11"
    ]
    }

# 6. 팀원 소개 (SSAFY 7기 A201)

<table>
    <tr>
        <td align="center"><img src="./img/teammates/inyoung.jpg" width="100px"></td>
        <td align="center"><img src="./img/teammates/jaeik.jpg" height="90px" width="70px"></td>
        <td align="center"><img src="./img/teammates/jongeun.jpg" width="70px" height="90px"></td>
    </tr>
    <tr>
        <td align="center" >팀장 류인영</td>
        <td align="center">팀원 이재익</td>
        <td align="center">팀원 이종은</td>
    </tr>
    <tr>
        <td align="center">#Backend #Server</td>
        <td align="center">#Frontend #Publishing</td>
        <td align="center">#Frontend #Axios</td>
    </tr>
    <tr>
        <td align="center"><img src="./img/teammates/sunyoung.jpg" width="100px"></td>
        <td align="center"><img src="./img/teammates/donghyun.jpg" height="100px"></td>
        <td align="center"><img src="./img/teammates/sanghyup.jpg" height="100px"></td>
    </tr>
    <tr>
        <td align="center">팀원 전선영</td>
        <td align="center">팀원 한동현</td>
        <td align="center">팀원 김상협</td>
    </tr>
    <tr>
        <td align="center">#Backend #Planning</td>
        <td align="center">#Data Parse & Crawl</td>
        <td align="center">#Frontend #Backend</td>
    </tr>
</table>


