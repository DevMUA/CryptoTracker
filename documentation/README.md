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
<p>As a rookie cryptocurrency investor, after I bought 1 Ether at 2000 euros I want to receive a warning of when the price of Ether goes above 2200 so that I can exchange it with 200 euros of profit</p>
<h3>User Story 2:</h3>
<p>As logged user I want to see prediction of price of Ethereum in next five hours, to decide if I should sell it</p>
<h3>User Story 3:</h3>
<p>As logged user I want to see graph with price of Dogecoin during last five years, so that I can plan my investments.</p>
<h3>User Story 4:</h3>
<p>As logged user I want to be able to receive news about Litecoin, to be up-to-date</p>
<h3>User Story 5:</h3>
<p>As non logged user, I want to be able to login so that I can access my account/account settings</p>



<h2>Team:</h2>
To know who is behind this project go over the <a href="https://github.com/DevMUA/CryptoTracker/blob/main/documentation/team/about.md">team section</a>.
