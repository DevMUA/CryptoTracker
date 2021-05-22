from kafka import KafkaConsumer
from multiprocessing import Process
from json import dumps, loads
from multiprocessing import Pool
import requests
import time

KAFKA_SERVER = 'localhost:9092'
CRYPTOS = ['bitcoin', 'dogecoin', 'ethereum']

def consumeData(topic):
    try:
        consumer = KafkaConsumer(
            topic,
            bootstrap_servers=[KAFKA_SERVER],
            auto_offset_reset='earliest', #not sure if we want 'latest'
            enable_auto_commit=True,
            group_id=topic+'_test',
            value_deserializer=lambda v: loads(v.decode('utf-8')))
    except:
        print("Error!!")
    for msg in consumer:
        print(msg.value)
        #robienie i zapisywanie predykcji
        #probably model.predict(msg.value)

def train_model(crypto):
    task = {"crypto": crypto, "input": [[0,2,4,6], [1,3,5,7]]}
    resp = requests.post('http://app:5000/train/', json=task)
    print(resp.text)


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
    time.sleep(5)
    train_model('bitcoin')
    try:
        pool_size = len(CRYPTOS)
        pool = Pool(pool_size)
        pool.map(consumeData, CRYPTOS)
    except:
        print ("Error: unable")


