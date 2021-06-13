from numpy.lib.function_base import average
from pycoingecko import CoinGeckoAPI
from numpy import array, random
from joblib import dump, load

CRYPTO = ['bitcoin', 'dogecoin', 'ethereum']
cg = CoinGeckoAPI()

def get_cryptos():
    return CRYPTO
 
# split a univariate sequence into samples
# def split_sequence(sequence, n_steps_in, n_steps_out):
# 	X, y = list(), list()
# 	for i in range(len(sequence)):
# 		# find the end of this pattern
# 		end_ix = i + n_steps_in
# 		out_end_ix = end_ix + n_steps_out
# 		# check if we are beyond the sequence
# 		if out_end_ix > len(sequence):
# 			break
# 		# gather input and output parts of the pattern
# 		seq_x, seq_y = sequence[i:end_ix], sequence[end_ix:out_end_ix]
# 		X.append(seq_x)
# 		y.append(seq_y)
# 	return array(X), array(y)


# def train_test_split(X, y, ratio=0.8):
#     length = len(X)
#     divider = int(length*ratio)
#     X_train = X[:divider]
#     y_train = y[:divider]
#     X_test = X[divider:]
#     y_test = y[divider:]

#     return X_train, y_train, X_test, y_test

# def scale_data(data, scaler=None):
#     if scaler is None:
#         # create scaler
#         scaler = MinMaxScaler(feature_range=(0,1))
#         scaler.fit(data)
#     else:
#         scaler = scaler
#     # apply transform
#     normalized = scaler.transform(data)
#     return normalized, scaler
        

class SequentialOutputModel():
    def __init__(self):
        pass

    # def train(self, raw_seq, n_steps_in, n_steps_out):
    #     X, y = split_sequence(raw_seq, n_steps_in, n_steps_out)

    #     # X_train, y_train, X_test, y_test = train_test_split(X, y)
    #     # # reshape from [samples, timesteps] into [samples, timesteps, features]
    #     # n_features = 1
    #     # X_train, self.scaler = scale_data(X_train)
    #     # X_test, _ = scale_data(X_test, self.scaler)
    #     # X_train = X_train.reshape((X_train.shape[0], X_train.shape[1], n_features))
    #     # X_test = X_test.reshape((X_test.shape[0], X_test.shape[1], n_features))

    #     # reshape from [samples, timesteps] into [samples, timesteps, features]
    #     n_features = 1
    #     X, self.scaler = scale_data(X)
    #     X = X.reshape((X.shape[0], X.shape[1], n_features))

    #      # define model
    #     model = Sequential()
    #     # model.add(LSTM(100, activation='relu', return_sequences=True, input_shape=(n_steps_in, n_features)))
    #     # model.add(LSTM(100, activation='relu'))
    #     # model.add(Dense(n_steps_out))
    #     n_features = 1
    #     dropout = 0.2
    #     model.add(Bidirectional(LSTM(100, activation='relu', return_sequences=True, input_shape=(n_steps_in, n_features))))
    #     model.add(Dropout(rate=dropout))

    #     model.add(Bidirectional(LSTM(100, activation='relu', return_sequences=True)))
    #     model.add(Dropout(rate=dropout))

    #     model.add(Bidirectional(LSTM(100, activation='relu')))

    #     model.add(Dense(n_steps_out))

    #     model.add(Activation('linear'))
    #     model.compile(optimizer='adam', loss='mse')

    #     # fit model
    #     self.history = model.fit(X, y, epochs=50, verbose=1)
    #     self.model = model

    #     return self.model


    def predict(self, data):
        # n_steps_in = len(data)
        # data = array([data])
        # data, _ = scale_data(data, self.scaler)
        # x_input = data.reshape((1, n_steps_in, 1))
        # yhat = self.model.predict(x_input, verbose=0)
        return True
        
    def save(self, name):
        # self.model.save('saved_models/'+name)
        # dump(self.scaler, 'saved_models/'+name+'_scaler')
        return True

    def load(self, name):
        # self.model = load_model('saved_models/'+name)
        # self.scaler = load('saved_models/'+name+'_scaler')
        return True


if __name__ == "__main__":
    for crypto in CRYPTO:
        prices = cg.get_coin_market_chart_by_id('dogecoin', 'usd', 'max')
        prices = [x[1] for x in prices['prices']]
        

        # model = SequentialOutputModel()
        # model.train(prices, 96, 5)
        # # rand_array = random.randint(150, size=30)
        # # prediction = model.predict(rand_array)
        # # print(prediction)
        # model.save(crypto)
        # new_model = SequentialOutputModel()
        # new_model.load('dogecoin')
        # new_prediction = new_model.predict(rand_array)
        # print(new_prediction)
    
       