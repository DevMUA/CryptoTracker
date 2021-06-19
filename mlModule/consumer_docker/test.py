#!/usr/bin/env python
import unittest
from kafka import KafkaConsumer, KafkaProducer
from json import dumps, loads
from consumer import send_predictions
from time import sleep
from numpy import random
from mlModule.consumer_docker.model import SequentialOutputModel

unittest.TestLoader.sortTestMethodsUsing = None
class PipelineTest(unittest.TestCase):
    def setUp(self) -> None:
        self.path = 'mlModule/consumer_docker/'

    def test_dummy(self):
        self.assertEqual(1,1) 

    def test_model_create(self):
        self.model = SequentialOutputModel()
        self.assertTrue(True)

    def test_model_load(self):
        self.model = SequentialOutputModel()
        self.model.load('bitcoin', self.path+'saved_models/')
        self.assertTrue(True)

    def test_prediction(self):
        self.model = SequentialOutputModel()
        self.model.load('bitcoin', self.path+'saved_models/')
        rand_array = random.randint(150, size=96)
        prediction = self.model.predict(rand_array)
        self.assertEqual(len(prediction[0]), 5)
        

if __name__ == '__main__':
    import xmlrunner
    runner = xmlrunner.XMLTestRunner(output='test-reports')
    unittest.main(testRunner=runner)
    unittest.main()
