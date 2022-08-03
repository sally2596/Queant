#아래 적힌 이미지를 베이스 이미지로 한다.
#이 명령어가 실행되면 Dockerhub에서 해당 이미지를 pull한다.
FROM python:3.10.5
# 환경변수 설정
ENV PYTHONUNBUFFERED 1  
# docker 내에서 /data 라는 이름의 폴더 생성
RUN mkdir /data 
# docker 내에서 코드를 실행할 폴더 위치를 /data 로 지정
WORKDIR /data
# 로컬의 requirements.txt 파일을 docker /code/ 폴더로 마운트
ADD ./data/queant/requirements.txt /data/ 
# docker 내 requirements.txt 파일을 이용하여 패키지 설치
RUN pip install -r requirements.txt 
# 로컬 내 현재 위치에 있는 모든 파일 및 폴더를 docker 의 /data/ 폴더로 마운트
ADD ./data /data/ 
EXPOSE 8600
CMD ["python", "./queant/manage.py", "runserver", "0.0.0.0:8000"]