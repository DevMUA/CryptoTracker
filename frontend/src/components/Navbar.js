import React, { useState, useRef, useEffect } from "react";
import { Link, Redirect, useHistory } from "react-router-dom";
import { FaBars } from "react-icons/fa";
import { MdNotifications } from "react-icons/md";
import { CgProfile } from "react-icons/cg";
import { links } from "../links";
import sublinks from "../sublinks";
import logo from "../logo3.svg";
import Badge from "@material-ui/core/Badge";
import { Popover } from "@material-ui/core";
import { useGlobalContext } from "../context";

const Navbar = () => {
  const [showLinks, setShowLinks] = useState(false);
  const { signedIn, setSignedIn, email, setGlobalEmail } = useGlobalContext();
  const [anchorProfile, setAnchorProfile] = useState(null);
  const [anchorNotification, setAnchorNotification] = useState(null);
  const linksContainerRef = useRef(null);
  const linksRef = useRef(null);
  const [notifications, setNotifications] = useState([]);
  const [listening, setListening] = useState(false);
  let eventSource = undefined;
  const history = useHistory();

  console.log(localStorage.getItem("notifications"));
  if (localStorage.getItem("notifications") === null)
    localStorage.setItem("notifications", JSON.stringify([]));

  if (localStorage.getItem("isListening") === null)
    localStorage.setItem("isListening", "false");

  useEffect(() => {
    var isListening = localStorage.getItem("isListening");
    console.log(isListening);
    if (isListening === "false") {
      var emailToUse = localStorage.getItem("email");
      console.log("listening with email " + emailToUse);
      eventSource = new EventSource(
        "http://localhost:9090/listen=" + emailToUse
      );

      eventSource.onopen = (event) => {
        console.log("connection opened");
      };

      eventSource.onmessage = (event) => {
        console.log("result", event.data);
        console.log(event.data.length);
        if (event.data.length === 2) return;
        var notificationss = JSON.parse(localStorage.getItem("notifications"));
        notificationss.push(event.data);
        localStorage.setItem("notifications", JSON.stringify(notificationss));
        history.go(0);
        //setNotifications((old) => [...old, event.data]);
      };

      eventSource.onerror = (event) => {
        console.log("error");
        console.log(event.target.readyState);
        if (event.target.readyState === EventSource.CLOSED) {
          console.log("eventsource closed (" + event.target.readyState + ")");
        }
        eventSource.close();
      };

      localStorage.setItem("isListening", "true");
    }

    return () => {
      eventSource.close();
      console.log("eventsource closed");
    };
  }, []);

  const toggleLinks = () => {
    setShowLinks(!showLinks);
  };
  useEffect(() => {
    const linksHeight = linksRef.current.getBoundingClientRect().height;
    if (showLinks) {
      linksContainerRef.current.style.height = `${linksHeight}px`;
    } else {
      linksContainerRef.current.style.height = "0px";
    }
  }, [showLinks]);

  useEffect(() => {
    if (localStorage.getItem("signedIn")) {
      setSignedIn(localStorage.getItem("signedIn"));
    }
    if (localStorage.getItem("email")) {
      setGlobalEmail(localStorage.getItem("email"));
    }
  }, []);

  const openProfile = (e) => {
    setAnchorProfile(e.currentTarget);
  };

  const openNotification = (e) => {
    setAnchorNotification(e.currentTarget);
  };

  const renderProfile = () => {
    return (
      <Popover
        open={Boolean(anchorProfile)}
        anchorEl={anchorProfile}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
        transformOrigin={{ vertical: "top", horizontal: "center" }}
        onClose={() => {
          setAnchorProfile(null);
        }}
      >
        <div className="submenu">
          {sublinks.map((item, index) => {
            const { links, page } = item;
            return (
              <article key={index}>
                <div className="sidebar-sublinks">
                  <a href="">{email}</a>
                  {links.map((link, index) => {
                    const { url, icon, label } = link;
                    var tmp = signedIn === "true";
                    //tmp = true;
                    if (
                      tmp === true &&
                      (label === "Login" || label === "Register")
                    ) {
                      return "";
                    }
                    if (
                      tmp === false &&
                      (label === "Account Settings" || label === "Logout")
                    ) {
                      return "";
                    }
                    return (
                      <a key={index} href={url}>
                        {icon}
                        {label}
                      </a>
                    );
                  })}
                </div>
              </article>
            );
          })}
        </div>
      </Popover>
    );
  };

  const renderNotifications = () => {
    if (signedIn) {
      return (
        <Popover
          open={Boolean(anchorNotification)}
          anchorEl={anchorNotification}
          anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
          transformOrigin={{ vertical: "top", horizontal: "center" }}
          onClose={() => {
            setAnchorNotification(null);
            localStorage.setItem("notifications", JSON.stringify([]));
          }}
        >
          <div className="submenu">
            <article>
              <div className="sidebar-sublinks">
                {JSON.parse(localStorage.getItem("notifications")).map(
                  (item, index) => {
                    //var obj = JSON.parse(item);
                    return (
                      <a key={index} href="">
                        {item}
                      </a>
                    );
                  }
                )}
              </div>
            </article>
          </div>
        </Popover>
      );
    }
  };

  return (
    <nav>
      <div className="nav-center">
        <div className="nav-header">
          <img src={logo} className="logo" alt="logo" />
          <button className="nav-toggle" onClick={toggleLinks}>
            <FaBars />
          </button>
        </div>
        <div className="links-container" ref={linksContainerRef}>
          <ul className="links" ref={linksRef}>
            {links.map((link) => {
              const { id, url, text } = link;
              return (
                <li key={id}>
                  <a href={url}>{text}</a>
                </li>
              );
            })}
          </ul>
        </div>
        <div className="user-container">
          <ul className="options">
            {renderNotifications()}
            <li onClick={openNotification}>
              <Badge
                badgeContent={
                  JSON.parse(localStorage.getItem("notifications")).length
                }
                color="secondary"
              >
                <MdNotifications size={30} />
              </Badge>
            </li>
            {renderProfile()}
            <li onClick={openProfile}>
              <Badge color="secondary">
                <CgProfile size={40} />
              </Badge>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
