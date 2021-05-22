from flask import Flask, request
import flask.scaffold
flask.helpers._endpoint_from_view_func = flask.scaffold._endpoint_from_view_func
import flask_restful
from flask_restful import reqparse, Api, Resource, abort
from model import predict_rest, get_cryptos, plug_prediction
import json

app = Flask(__name__)
api = Api(app)

def abort_if_crypto_doesnt_exist(crypto):
    cryptos = get_cryptos()
    if crypto not in cryptos:
        abort(404, message="Crypto {} doesn't exist".format(crypto))


class Prediction(Resource):
    def get(self, crypto):
        abort_if_crypto_doesnt_exist(crypto)
        prediction = plug_prediction() #tochange
        print(prediction)
        return prediction


class TrainModel(Resource):
    def post(self):
        json_data = request.get_json(force=True)
        crypto = json_data['crypto']
        input = json_data['input']
        print(crypto, input) #trainmodel
        return crypto


##
## Actually setup the Api resource routing here
##
api.add_resource(Prediction, '/prediction/<crypto>')
api.add_resource(TrainModel, '/train/')


if __name__ == '__main__':
    app.run(debug=True)