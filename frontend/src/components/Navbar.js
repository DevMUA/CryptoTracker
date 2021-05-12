import React, { useState, useRef, useEffect } from "react";
import { FaBars } from "react-icons/fa";
import { MdNotifications } from "react-icons/md";
import { CgProfile } from "react-icons/cg";
import { links } from "../links";
import logo from "../logo3.svg";
import Badge from "@material-ui/core/Badge";
import { Popover } from "@material-ui/core";

const Navbar = () => {
  const [showLinks, setShowLinks] = useState(false);
  const [signedIn, setSignedIn] = useState(true);
  const [anchor, setAnchor] = useState(null);
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
    setAnchor(e.currentTarget);
  };

  const renderNotification = () => {
    if (signedIn) {
      return (
        <li>
          <Badge badgeContent={1} color="secondary">
            <MdNotifications size={30} />
          </Badge>
          <Popover
            open={Boolean(anchor)}
            anchorEl={anchor}
            anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
            transformOrigin={{ vertical: "center", horizontal: "center" }}
            onClose={() => setAnchor(null)}
          >
            <div className="submenu"></div>
          </Popover>
        </li>
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
            {renderNotification()}
            <li onClick={openProfile}>
              <CgProfile size={40} />
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
