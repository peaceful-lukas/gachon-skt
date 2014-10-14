# 가천대학교 skt 산학 프로젝트

## Install

** NOTE: ** 본 프로젝트는 Eclipse Luna, JDK 1.8 환경에서 구축되었습니다. 

1. Eclipse > Window > Eclipse Marketplace > maven 검색 > Maven Integration for Eclipse (Luna and newer) 1.5 설치
2. Eclipse > File > import > General > Existing Projects into Workspace > Select root directory 에서 본 프로젝트 디렉토리 선택 후 import 완료
3. Eclipse 창 왼편의 Project Explorer 에서 해당 프로젝트 최상위 폴더를 누른 후 F5 키를 눌러 새로고침. (maven dependency 걸린 library 들이 자동으로 설치됨)
4. classpath 및 build path 등을 적절히 수정.
5. resources/config.xml 에서 실행 환경에 맞게 설정.
5. com.lukaslab.proj.skt.AppExecutor 에서 실행.

** 유의사항: ** libs/ojdbc7.jar 의 경우 maven repository 에 등록되어 있지 않아서 별도로 추가했으므로 따로 build path 에 추가시켜야 한다.



