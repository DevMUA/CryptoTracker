FROM python:3.6-slim-buster
WORKDIR /app
COPY requirements.txt requirements.txt
RUN pip3 install --upgrade pip
RUN pip3 install psycopg2-binary
RUN pip3 install -r requirements.txt
COPY . .
EXPOSE 8000
CMD [ "python3", "test.py"]
