#!/usr/bin/env python
import unittest
from kafka import KafkaConsumer, KafkaProducer
from json import dumps, loads
from consumer import send_predictions
from time import sleep
from numpy import random
from model import SequentialOutputModel

unittest.TestLoader.sortTestMethodsUsing = None
class PipelineTest(unittest.TestCase):
    def setUp(self) -> None:
        self.path = ''

    def test_dummy(self):
        self.assertEqual(1,1) 

    def test_model_create(self):
        self.model = SequentialOutputModel()
        self.assertTrue(True)

    def test_dummy_predcition(self):
        self.model = SequentialOutputModel()
        predictions = self.model.dummy_prediction([1,2,3,4,5])
        self.assertAlmostEqual(len(predictions), 24)

    # def test_model_load(self):
    #     self.model = SequentialOutputModel()
    #     self.model.load('bitcoin')
    #     self.assertTrue(True)

    # def test_prediction(self):
    #     self.model = SequentialOutputModel()
    #     self.model.load('bitcoin')
    #     rand_array = random.randint(150, size=96)
    #     prediction = self.model.predict(rand_array)
    #     self.assertEqual(len(prediction[0]), 5)
        

if __name__ == '__main__':
    import xmlrunner
    runner = xmlrunner.XMLTestRunner(output='test-reports')
    unittest.main(testRunner=runner)
    unittest.main()
