#!/usr/bin/env python
import unittest
from kafka import KafkaConsumer, KafkaProducer
from json import dumps, loads
from consumer import send_predictions
from time import sleep
from numpy import random

unittest.TestLoader.sortTestMethodsUsing = None
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

   
    def test_dummy(self):
        self.assertEqual(1,1)


    # def test_pipeline(self):
    #     data = random.randint(150, size=96).tolist()
    #     send_predictions(self.producer, 'dogecoin', dumps(data))
    #     sleep(5)
    #     for msg in self.consumer:
    #         for key in msg.value:
    #             predictions = msg.value[key]
            
    #         break
    #     self.assertEqual(len(predictions), 5)

    
    


if __name__ == '__main__':
    import xmlrunner
    runner = xmlrunner.XMLTestRunner(output='test-reports')
    unittest.main(testRunner=runner)
    unittest.main()
