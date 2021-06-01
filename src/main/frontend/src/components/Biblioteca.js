import React, { useEffect, useState } from "react";
import NavBar from "./NavBar";
import axios from "axios";
import { useParams } from "react-router";

const Biblioteca = () => {
    const {id} = useParams();
    const[data, setData] = useState({})

    useEffect(() => {
        getLibros();
      }, []);

    const getLibros = () => {
        const header = {
            authorization: sessionStorage.getItem("accessToken"),
          };

        axios.get(`http://localhost:8080/grupos/${id}/biblioteca`, {headers: header})
        .then((response) => {
            setData(response)
        })
    }

    return (
        <div className="App">
          <NavBar/>
          <div className="body-container">
            <div className="main-content">
                {data.biblioteca?.map((libro) =>(
                    <div className="card">
                        <span>
                        {libro.nombre}
                        {libro.autores}
                        {libro.link}
                        </span>
                    </div>
                ))}
            </div>
            <div>
                
            </div>
          </div>
        </div>
    );
  }

  export default Biblioteca;


