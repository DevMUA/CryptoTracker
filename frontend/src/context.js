import React, { useState, useContext, useEffect } from "react";
import sublinks from "./sublinks";
const AppContext = React.createContext();

const AppProvider = ({ children }) => {
  const [signedIn, setSignedIn] = useState(false);
  const [email, setGlobalEmail] = useState("Not Signed In");

  useEffect(() => {
    if (localStorage.getItem("signedIn")) {
      setSignedIn(localStorage.getItem("signedIn"));
    }
    if (localStorage.getItem("email")) {
      setGlobalEmail(localStorage.getItem("email"));
    }
  }, []);

  return (
    <AppContext.Provider
      value={{ signedIn, setSignedIn, email, setGlobalEmail }}
    >
      {children}
    </AppContext.Provider>
  );
};
// make sure use
export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
