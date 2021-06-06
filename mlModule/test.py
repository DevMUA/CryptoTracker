#!/usr/bin/env python
import unittest
from kafka import KafkaConsumer, KafkaProducer
from json import dumps, loads
from consumer import send_predictions
from time import sleep

class PipelineTest(unittest.TestCase):
    def setUp(self) -> None:
        KAFKA_SERVER = '192.168.160.18:9092'
        self.consumer = KafkaConsumer(
            'dogecoin_prediction',
            bootstrap_servers=[KAFKA_SERVER],
            auto_offset_reset='earliest', #not sure if we want 'latest'
            enable_auto_commit=True,
            group_id='dogecoin_test',
            value_deserializer=lambda v: loads(v.decode('utf-8')))
        
        self.producer = KafkaProducer(bootstrap_servers=[KAFKA_SERVER],
                            value_serializer=lambda x: 
                            dumps(x).encode('utf-8'))

    def test_pipeline(self):
        data = [1,2,3,4,5]
        send_predictions(self.producer, 'dogecoin', data)
        sleep(5)
        for msg in self.consumer:
            for key in msg.value:
                predictions = msg.value[key]
            
            break
        self.assertEqual(3, predictions)
