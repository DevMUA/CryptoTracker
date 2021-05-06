<h1>Architecture</h1>

![Alt text](../images/Overview.png?raw=true "Architecture")

<h2>Data gathering:</h2>

In the diagram above the information retrieval module works on establishing a communication with the CoinGecko API and pushing that information into Kafka, that information which
will be present in a topic will also later on be consumed by the different applications listening on it.

<h2>Backend:</h2>

The backend consists of the various features to be implemented separated on their own applications such as to follow a microservice architecture.

<h3>Database Application:</h3>
This application will store the information on a permanent database to be used by any other component, it will produce information to a kafka topic periodically.
It uses JAVA as its language supported by Springboot.
<br/><br/>

  <strong>KAFKA TOPICS TBD:</strong>
  
  <ul>
    <li>#</li>
  </ul>
  
<h3>Machine Learning Application:</h3>
This application will provide the feature of stock prediction, it will use a machine learning model to do such prediction and it will then produce the outcome to a kafka topic.
It uses Python as its language.

  <br/><br/>
  <strong>KAFKA TOPICS TBD:</strong>
  
  <ul>
    <li>#</li>
  </ul>
  
  <h3>REST API Application:</h3>
 This application establishes the communication channel to the frontend of the project, it will provide a REST API that the frontend will use and realtime communication to
 push events.
 It uses JAVA as its language supported by Springboot.

<br/><br/>
  <strong>KAFKA TOPICS TBD:</strong>
  
  <ul>
    <li>#</li>
  </ul>
  
  <br/><br/>
  <strong>Rest API:</strong>
  
  <ul>
    <li>#</li>
  </ul>
  
    <h3>Frontend Application:</h3>
    This application is what the user interacts with and will be using React.js
