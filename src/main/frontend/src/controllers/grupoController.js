import axios from "axios";

export function salirDelGrupo(id) {
    const header = {authorization: sessionStorage.getItem("accessToken")};
    const data = {userName: sessionStorage.getItem("loggedUsername")};
    axios
        .post(`http://localhost:8080/grupos/${id}/salir`, data, {
            headers: header,
        })
        .catch((error) => console.log(error));
}