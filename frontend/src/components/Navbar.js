import React, { useState, useRef, useEffect } from "react";
import { FaBars } from "react-icons/fa";
import { MdNotifications } from "react-icons/md";
import { CgProfile } from "react-icons/cg";
import { links } from "../links";
import sublinks from "../sublinks";
import logo from "../logo3.svg";
import Badge from "@material-ui/core/Badge";
import { Popover } from "@material-ui/core";

const Navbar = () => {
  const [showLinks, setShowLinks] = useState(false);
  const [signedIn, setSignedIn] = useState(true);
  const [anchorProfile, setAnchorProfile] = useState(null);
  const [anchorNotification, setAnchorNotification] = useState(null);
  const linksContainerRef = useRef(null);
  const linksRef = useRef(null);
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
                  {links.map((link, index) => {
                    const { url, icon, label } = link;
                    if (signedIn && (label === "Login" || label === "Register"))
                      return "";
                    if (
                      !signedIn &&
                      label !== "Login" &&
                      label !== "Register"
                    ) {
                      return "";
                    }
                    console.log(label);
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
          onClose={() => setAnchorNotification(null)}
        >
          <div className="submenu">
            <article>
              <div className="sidebar-sublinks">No notifications</div>
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
              <Badge badgeContent={0} color="secondary">
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
