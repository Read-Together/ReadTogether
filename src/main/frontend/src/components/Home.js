import React from "react";
import NavBar from "./NavBar";
import {LeftMenu} from "./LeftMenu";

const Home = () => {

    return (
        <div className="App">
          <NavBar/>
          <div className="body-container">
            <LeftMenu/>
            <div className="main-content">
            </div>
          </div>
        </div>
    );
  }

  export default Home;