from kafka import KafkaConsumer, KafkaProducer
from multiprocessing import Process
from json import dumps, load, loads
from multiprocessing import Pool
from model import SequentialOutputModel
from datetime import datetime
from datetime import timedelta
import psycopg2

KAFKA_SERVER = '192.168.160.18:9092'
CRYPTOS = ['bitcoin', 'dogecoin', 'ethereum']


def send_predictions(producer, name, predictions):
    producer.send(name, value=predictions)
    producer.flush()


def create_database():
    conn = psycopg2.connect(
    host="192.168.160.18",
    database="postgres",
    user="postgres",
    password="postgres")

    cur = conn.cursor()

    sql = """CREATE TABLE IF NOT EXISTS cryptos(
        id SERIAL PRIMARY KEY,
        name VARCHAR(255),
        date DATE,
        prediction JSON
        )"""

    cur.execute(sql)

    conn.commit()

    cur.close()
    print("Database created")


def consumeData(topic):
    try:
        consumer = KafkaConsumer(
            topic,
            bootstrap_servers=[KAFKA_SERVER],
            auto_offset_reset='earliest', #not sure if we want 'latest'
            enable_auto_commit=True,
            group_id=topic+'_test',
            value_deserializer=lambda v: loads(v.decode('utf-8')))
        
        producer = KafkaProducer(bootstrap_servers=['192.168.160.18:9092'],
                            value_serializer=lambda x: 
                            dumps(x).encode('utf-8'))
    except:
        print("Error!!")

    for msg in consumer:
        value = msg.value
        model = SequentialOutputModel()
        model.load(topic)
        prediction = model.predict(value)
        date = datetime.now()
        crypto = topic
        prediction_date = datetime.now()
        prediction_date = prediction_date.strftime("%m/%d/%Y, %H:%M:%S")

        conn = psycopg2.connect(
        host="192.168.160.18",
        database="postgres",
        user="postgres",
        password="postgres")

        cur = conn.cursor()

        sql = """INSERT INTO cryptos (name, date, prediction) VALUES (%s, %s, %s)"""

        cur.execute(sql, (crypto, date, dumps({prediction_date: prediction})))

        conn.commit()

        cur.close()

        cur = conn.cursor()

        sql = """SELECT prediction FROM cryptos WHERE name='dogecoin' ORDER BY date DESC"""
        cur.execute(sql)
        predictions = cur.fetchall()[0][0]
        send_predictions(producer, topic+'_prediction', predictions)

def get_crypto():
    try:
        consumer = KafkaConsumer(
            'all_cryptos',
            bootstrap_servers=[KAFKA_SERVER],
            auto_offset_reset='earliest', #not sure if we want 'latest'
            enable_auto_commit=True,
            group_id='cryptos',
            value_deserializer=lambda v: loads(v.decode('utf-8')))
    except:
        print("Error!!")
    for msg in consumer:
        CRYPTOS = msg.value

if __name__ == '__main__':
    create_database()
    try:
        pool_size = len(CRYPTOS)
        pool = Pool(pool_size)
        pool.map(consumeData, CRYPTOS)
    except:
        print ("Error: unable")

