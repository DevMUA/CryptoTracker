import logo from "./logo.svg";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";

// import pages
import Home from "./pages/Home";
import Error from "./pages/Error";
import Livefeed from "./pages/Livefeed";
import News from "./pages/News";
import AccountSettings from "./pages/AccountSettings";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";

// import components
import Navbar from "./components/Navbar";
import "typeface-roboto";
import CryptoCurrencyPage from "./pages/CryptoCurrencyPage";
function App() {
  return (
    <Router>
      <Navbar />
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
        <Route exact path="/livefeed">
          <Livefeed />
        </Route>
        <Route exact path="/news">
          <News />
        </Route>
        <Route exact path="/settings">
          <AccountSettings />
        </Route>
        <Route exact path="/login">
          <LoginPage />
        </Route>
        <Route exact path="/register">
          <RegisterPage />
        </Route>
        <Route path="/crypto/:name">
          <CryptoCurrencyPage />
        </Route>
        <Route path="*">
          <Error />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
