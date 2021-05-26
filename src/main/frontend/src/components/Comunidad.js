import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import NavBar from "./NavBar";


const Comunidad = () =>{

    const[data,setData] = useState({});
    const {id} = useParams();

    useEffect(() =>{
        getComunidad();
    }, []);
    
    const getComunidad = () =>{
        axios.get(`http://localhost:8080/comunidad/${id}`)
        .then((response) =>{
            setData(response.data)
        })
    }

    return(
        <div>
            <NavBar/>
            <p>{data.id}</p>
            <p>{data.nombre}</p>
            <p>{data.descripcion}</p>

        </div>
       
    )
}

export default Comunidad;

