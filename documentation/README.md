<h1>CryptoTracker:</h1>

CryptoTracker is an application that receives its information from CoinGecko API and over time stores the information in a Kafka Broker that is later consumed by the backend to be
stored in a database. The system will then use that information to pass it to the machine learning module that will make predictions, that information will then be exposed in a REST API
to be used by the frontend interface.

<h2>Features:</h2>
<ul>
  <li>Live information about cryptocurrencies price</li>
  <li>ML model assistance with actions on market</li>
  <li>Alarm notification on the front-end if the crypto prices reaches a certain threshold</li>
</ul>

<h2>User Stories:</h2>
<h3>User Story 1:</h3>
<p>As a rookie cryptocurrency investor, I want to be able to receive a warning when the price of ethereum goes 100 euros above the price I bought it so that I can sell it with a good profit margin.</p>
<h3>User Story 2:</h3>
<p>As [some user], I want to know when the price will Bitcoin will go under 50000 USD so that I can exchange before losing money.</p>
<h3>User Story 3:</h3>
<p>As a long term investor, I wish to see the evolution of several cryptocurrencies over a period of several years so that I can plan my investments.</p>
<h3>User Story 4:</h3>
<p>As [some user], I want to be able to pick the cryptocurrencies I'm most interested and receive news related to them so I can do better choices</p>
<h3>User Story 5:</h3>
<p>As [some user], I want to be able to login so that I can access my account/account settings</p>



<h2>Team:</h2>
To know who is behind this project go over the <a href="https://github.com/DevMUA/CryptoTracker/blob/main/documentation/team/about.md">team section</a>.
