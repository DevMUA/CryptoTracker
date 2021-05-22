from pycoingecko import CoinGeckoAPI
import numpy as np
import tensorflow as tf
from tensorflow import keras
import pandas as pd
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.layers import Bidirectional, Dropout, Activation, Dense, LSTM
from tensorflow.python.keras.layers import CuDNNLSTM
from tensorflow.keras.models import Sequential
import matplotlib.pyplot as plt
from matplotlib import rc
from pickle import dump, load
from datetime import datetime
from random import randint

CRYPTO = ['bitcoin', 'dogecoin', 'ethereum']
cg = CoinGeckoAPI()

def get_cryptos():
    return CRYPTO

def to_sequences(data, seq_len):
    d = []

    for index in range(len(data) - seq_len):
        d.append(data[index: index + seq_len])

    return np.array(d)

def preprocess(data_raw, seq_len, train_split):

    data = to_sequences(data_raw, seq_len)

    num_train = int(train_split * data.shape[0])

    X_train = data[:num_train, :-1, :]
    y_train = data[:num_train, -1, :]

    X_test = data[num_train:, :-1, :]
    y_test = data[num_train:, -1, :]

    return X_train, y_train, X_test, y_test


def prepare_data(data, scaler_name, SEQ_LEN=100):
    df = pd.DataFrame(data)
    df.columns = ['Timestamp', 'Value']

    scaler = MinMaxScaler()
    close_price = df.Value.values.reshape(-1, 1)
    scaled_close = scaler.fit_transform(close_price)
    dump(scaler, open(scaler_name+'_scaler.pkl', 'wb'))

    X_train, y_train, _, _ = preprocess(scaled_close, SEQ_LEN, train_split = 1)
    return X_train, y_train

#X_train, y_train, X_test, y_test = preprocess(scaled_close, SEQ_LEN, train_split = 0.8)


def train_model(X_train, y_train, dropout=0.2, window_size=99):

    model = keras.Sequential()

    model.add(Bidirectional(LSTM(window_size, return_sequences=True),
                            input_shape=(window_size, X_train.shape[-1])))
    model.add(Dropout(rate=dropout))

    model.add(Bidirectional(LSTM((window_size * 2), return_sequences=True)))
    model.add(Dropout(rate=dropout))

    model.add(Bidirectional(LSTM(window_size, return_sequences=False)))

    model.add(Dense(units=1))

    model.add(Activation('linear'))

    model.compile(
        loss='mean_squared_error', 
        optimizer='adam'
    )

    BATCH_SIZE = 64

    history = model.fit(
        X_train, 
        y_train, 
        epochs=50, 
        batch_size=BATCH_SIZE, 
        shuffle=False,
        validation_split=0.1
    )

    return model

def save_model(model, name):
    model.save(name)

# y_test_inverse = scaler.inverse_transform(y_test)
# y_hat_inverse = scaler.inverse_transform(y_hat)

def predict(crypto, data):
    model = keras.models.load_model(crypto)
    scaler = load(open(crypto+'_scaler.pkl', 'rb'))
    df = pd.DataFrame(data)
    df.columns = ['Timestamp', 'Value']
    close_price = df.Value.values.reshape(-1, 1)
    scaled_close = scaler.transform(close_price)
    X_train, _, _, _ = preprocess(scaled_close, 100, train_split = 1)
    y = model.predict(X_train)
    prediction = scaler.inverse_transform(y)
    return prediction


def predict_rest(crypto):
    prices = cg.get_coin_market_chart_by_id(crypto, 'usd', 'max')

    one = prices['prices'][0]
    timestamp = int(one[0])
    dt_object = datetime.fromtimestamp(timestamp/1000)
    data = prices['prices']
    print('start prediction')
    prediction = predict(crypto,data)
    return prediction


def plug_prediction():
    return [randint(100,200) for i in range(24)]



if __name__ == "__main__":
    for crypto in CRYPTO:
        prices = cg.get_coin_market_chart_by_id(crypto, 'usd', 'max')
        one = prices['prices'][0]
        timestamp = int(one[0])
        dt_object = datetime.fromtimestamp(timestamp/1000)

        print("dt_object =", dt_object)
        print(prices['prices'][0:5])
        print(len(prices['prices']))
        print(50*24)
        data = prices['prices']
        X_train, y_train = prepare_data(data, crypto)
        model = train_model(X_train, y_train)
        save_model(model, crypto+'_model')