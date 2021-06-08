import React, {useEffect, useState} from "react";
import NavBar from "./NavBar";
import {LeftMenu} from "./LeftMenu";
import axios from "axios";
import { useParams } from "react-router";

const Home = () => {
  const[data, setData] = useState([]);

  useEffect(() => {
    getComunidades();
  }, []);

  const getComunidades = () => {
    const header = {
      authorization: sessionStorage.getItem("accessToken"),
    };

    const usuario = sessionStorage.getItem("loggedUsername")

    axios
      .get(`http://localhost:8080/home/${usuario}`, {headers: header})
      .then((response) => {
        setData(response.data)
        console.log(data);
      });
  }

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